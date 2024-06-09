package me.sit.dev.repository.impl.restaurant;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.repository.IRestaurantRepo;
import me.sit.dev.repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDatabaseRepo extends RestaurantMemoRepo implements IRestaurantRepo {

    private final Connection connection;

    public RestaurantDatabaseRepo() {
        super();
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }

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
                restaurantMap.put(id, restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
