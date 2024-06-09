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
        Connection c = null;
        try {
            c = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.connection = c;
        this.productRepo = productRepo;
        loadOrdersFromDatabase();
    }

    private void loadOrdersFromDatabase() {
        String sql = "SELECT * FROM CustomerOrder";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                String userId = rs.getString("userId");
                String restaurantName = rs.getString("restaurantName");
                String restaurantId = rs.getString("restaurantId");
                String productMap = rs.getString("productMap");
                String status = rs.getString("status");

                Order order = new Order(id, userId, restaurantId, restaurantName, deserializeProduct(productMap), status);
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

        String sql = "INSERT INTO CustomerOrder (id, userId, restaurantId, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, orderId);
            stmt.setString(2, user.getId());
            stmt.setString(3, restaurant.getName());
            stmt.setString(4, restaurant.getId());
            stmt.setString(5, serializeProduct(order.getProducts()));
            stmt.setString(6, order.getStatus().name());
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
        return order;
    }

    private String serializeProduct(Map<Product, Integer> map) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        for (Map.Entry<Product, Integer> entry : map.entrySet()) {
            Product product = entry.getKey();
            Integer amount = entry.getValue();
            stringBuilder.append(product.getId()).append("&&").append(product.getName()).append("=").append(amount).append(", ");
        }

        // Remove the trailing comma and space, if any
        if (stringBuilder.length() > 1) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private Map<Product, Integer> deserializeProduct(String hashMapAsString) {
        Map<Product, Integer> parsedHashMap = new HashMap<>();

        // Remove curly braces and split the string by commas to get key-value pairs
        String[] keyValuePairs = hashMapAsString.substring(1, hashMapAsString.length() - 1).split(", ");

        for (String pair : keyValuePairs) {
            String[] entry = pair.split("=");
            String[] productEntry = entry[0].split("&&");

            Product product = productRepo.findById(productEntry[0], productEntry[1]);
            int amount = Integer.parseInt(entry[1]);

            parsedHashMap.put(product, amount);
        }

        return parsedHashMap;
    }

}
