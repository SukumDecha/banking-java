package me.sit.dev.repository.impl.order;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.DatabaseConnection;
import me.sit.dev.repository.IProductRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OrderDatabaseRepo extends OrderMemoRepo {

    private final Connection connection;
    private final IProductRepo productRepo;

    public OrderDatabaseRepo(IProductRepo productRepo) {
        super();
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }

        this.productRepo = productRepo;
        loadOrdersFromDatabase();
    }

    private void loadOrdersFromDatabase() {
        String sql = "SELECT * FROM CustomerOrder";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            // execute =  ดำเนินการ
            // query =  ค้นหา
            while (rs.next()) {
                String id = rs.getString("id");
                String userId = rs.getString("userId");
                String restaurantName = rs.getString("restaurantName");
                String restaurantId = rs.getString("restaurantId");
                String productMap = rs.getString("productMap");
                String status = rs.getString("status");
                long orderAt = Long.parseLong(rs.getString("orderAt"));

                Order order = new Order(id, userId, restaurantId, restaurantName, deserializeProduct(productMap), status, orderAt);
                orderMap.put(id, order);
            }
        } catch (SQLException e) {
            System.out.println("Error loading orders from database: " + e.getMessage());
        }
    }

    @Override
    public Order createOrder(User user, Restaurant restaurant) {
        Order order = super.createOrder(user, restaurant);

        String orderId = "order-" + user.getId() + "-" + user.getOrders().size();

        String sql = "INSERT INTO CustomerOrder (id, userId, restaurantName, restaurantId, productMap, status, orderAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, orderId);
            stmt.setString(2, user.getId());
            stmt.setString(3, restaurant.getName());
            stmt.setString(4, restaurant.getId());
            stmt.setString(5, serializeProduct(restaurant.getId(), order.getProducts()));
            stmt.setString(6, order.getStatus().name());
            stmt.setString(7, String.valueOf(order.getOrderAt()));
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            return order;
        } catch (SQLException e) {
            System.out.println("Error creating order in database: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Order updateOrder(String orderId, Order order) {
        Order newOrder = super.updateOrder(orderId, order);

        String sql = "UPDATE CustomerOrder SET productMap = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, serializeProduct(order.getRestaurantId(), order.getProducts()));
            stmt.setString(2, order.getStatus().name());
            stmt.setString(3, orderId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating order failed, no rows affected.");
            }

            return newOrder;
        } catch (SQLException e) {
            System.out.println("Error updating order in database: " + e.getMessage());
        }

        return null;
    }

    private String serializeProduct(String restaurantId, Map<Product, Integer> map) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        for (Map.Entry<Product, Integer> entry : map.entrySet()) {
            Product product = entry.getKey();
            Integer amount = entry.getValue();
            stringBuilder.append(restaurantId).append("&&").append(product.getId()).append("=").append(amount).append(", ");
        }

        if (stringBuilder.length() > 1) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private Map<Product, Integer> deserializeProduct(String hashMapAsString) {
        Map<Product, Integer> parsedHashMap = new HashMap<>();

        String[] keyValuePairs = hashMapAsString.substring(1, hashMapAsString.length() - 1).split(", ");

        for (String pair : keyValuePairs) {
            String[] entry = pair.split("&&");
            String[] productEntry = entry[1].split("=");

            Product product = productRepo.findById(productEntry[0]);
            if(product == null) {
                System.out.println("Product not found: " + productEntry[0]);
                continue;
            }
            int amount = Integer.parseInt(productEntry[1]);

            parsedHashMap.put(product, amount);
        }

        return parsedHashMap;
    }

}
