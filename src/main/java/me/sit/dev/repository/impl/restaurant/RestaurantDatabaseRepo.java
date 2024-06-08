package me.sit.dev.repository.impl.restaurant;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.repository.IRestaurantRepo;
import me.sit.dev.repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantDatabaseRepo extends RestaurantMemoRepo implements IRestaurantRepo {

    private final Connection connection;

    public RestaurantDatabaseRepo() {
        Connection c = null;
        try {
            c = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.connection = c;
    }

    @Override
    public Restaurant addRestaurant(String ownerId, String restaurantName) {
        String sql = "INSERT INTO Restaurant (ownerId, name) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ownerId);
            stmt.setString(2, restaurantName);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating restaurant failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    String generatedId = String.valueOf(generatedKeys.getInt(1));
                    return new Restaurant(generatedId, ownerId, restaurantName, 0);
                } else {
                    throw new SQLException("Creating restaurant failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Restaurant updateRestaurant(String id, Restaurant restaurant) {
        String sql = "UPDATE Restaurant SET ownerId = ?, name = ?, totalRating = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, restaurant.getOwnerId());
            stmt.setString(2, restaurant.getName());
            stmt.setInt(3, restaurant.getTotalRating());
            stmt.setInt(4, Integer.parseInt(id));
            stmt.executeUpdate();
            return restaurant;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public Restaurant deleteRestaurant(String id) {
        String sql = "DELETE FROM Restaurant WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            stmt.executeUpdate();
            return findById(id); // Return the deleted restaurant object if needed
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }




}
