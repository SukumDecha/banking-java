package me.sit.dev.repository.impl.restaurant;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.repository.IOrderRepo;
import me.sit.dev.repository.IProductRepo;
import me.sit.dev.repository.IRestaurantRepo;
import me.sit.dev.repository.DatabaseConnection;
import me.sit.dev.service.ICartService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDatabaseRepo extends RestaurantMemoRepo implements IRestaurantRepo {

    private IOrderRepo orderRepo;
    private IProductRepo productRepo;
    private final Connection connection;

    public RestaurantDatabaseRepo(IOrderRepo orderRepo, IProductRepo productRepo) {
        super();
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }

        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        loadRestaurantsFromDatabase();
    }

    private void loadRestaurantsFromDatabase() {
        String sql = "SELECT * FROM Restaurant";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                String ownerId = rs.getString("ownerId");
                String name = rs.getString("name");
                int totalRating = rs.getInt("totalRating");

                Restaurant restaurant = new Restaurant(id, ownerId, name, totalRating);

                try (PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM `CustomerOrder` WHERE restaurantId = ?")) {
                    stmt2.setString(1, id);
                    ResultSet rs2 = stmt2.executeQuery();
                    List<Order> orders = new ArrayList<>();
                    while (rs2.next()) {
                        String orderId = rs2.getString("id");
                        Order order = orderRepo.findById(orderId);
                        orders.add(order);
                    }

                    restaurant.getOrders().addAll(orders);
                }

                try (PreparedStatement stmt3 = connection.prepareStatement("SELECT * FROM `Product` WHERE restaurantId = ?")) {
                    stmt3.setString(1, id);
                    ResultSet rs3 = stmt3.executeQuery();
                    List<Product> products = new ArrayList<>();

                    while (rs3.next()) {
                        String productId = rs3.getString("id");
                        Product product = productRepo.findById(productId);
                        products.add(product);
                    }

                    restaurant.getProducts().addAll(products);
                }

                restaurantMap.put(restaurant.getId(), restaurant);
                System.out.println("Restaurant loaded: " + restaurant.getName());
            }
        } catch (SQLException e) {
            System.out.println("Error loading restaurants from database: " + e.getMessage());
        }
    }


    @Override
    public Restaurant addRestaurant(String ownerId, String restaurantName) {
        Restaurant restaurant = super.addRestaurant(ownerId, restaurantName);

        String sql = "INSERT INTO Restaurant (id, ownerId, name, totalRating) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, restaurant.getId());
            stmt.setString(2, ownerId);
            stmt.setString(3, restaurantName);
            stmt.setInt(4, restaurant.getTotalRating());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating restaurant failed, no rows affected.");
            }

            return restaurant;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Restaurant updateRestaurant(String id, Restaurant restaurant) {
        restaurant = super.updateRestaurant(id, restaurant);

        String sql = "UPDATE Restaurant SET ownerId = ?, name = ?, totalRating = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, restaurant.getOwnerId());
            stmt.setString(2, restaurant.getName());
            stmt.setInt(3, restaurant.getTotalRating());
            stmt.setString(4, id);
            stmt.executeUpdate();
            return restaurant;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Restaurant deleteRestaurant(String id) {
        Restaurant restaurant = super.deleteRestaurant(id);
        String sql = "DELETE FROM Restaurant WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
            return restaurant;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
