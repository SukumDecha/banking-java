package me.sit.dev.repository.impl.user;

import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.entity.impl.user.UserRole;
import me.sit.dev.repository.IOrderRepo;
import me.sit.dev.repository.IUserRepo;
import me.sit.dev.repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDatabaseRepo extends UserMemoRepo implements IUserRepo {

    private final Connection connection;
    private final IOrderRepo orderRepo;

    public UserDatabaseRepo(IOrderRepo orderRepo) {
        super();
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }

        this.orderRepo = orderRepo;
        loadUsers();
    }

    private void loadUsers() {
        String sql = "SELECT * FROM User";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = loadUser(rs);
                userMap.put(user.getId(), user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load users from the database", e);
        }
    }

    private User loadUser(ResultSet rs) throws SQLException {
        String id = String.valueOf(rs.getString("id"));
        String name = rs.getString("name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String role = rs.getString("role");
        User user = new User(id, name, email, password, UserRole.valueOf(role));

        String restaurantId = rs.getString("restaurantId");
        if (restaurantId != null) {
            user.setRestaurantId(restaurantId);
        }

        try(PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `CustomerOrder` WHERE userId = ?")) {
            stmt.setString(1, id);
            ResultSet rs2 = stmt.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs2.next()) {
                String orderId = rs2.getString("id");
                Order order = orderRepo.findById(orderId);
                orders.add(order);
            }

            user.getOrders().addAll(orders);
        }

        return user;
    }

    @Override
    public User save(User user) {
        user = super.save(user);

        String sql = "INSERT INTO User (name, email, password, role, restaurantId, id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole().name());
            stmt.setString(5, user.getRestaurantId() == null ? null : user.getRestaurantId());
            stmt.setString(6, user.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User update(String userId, User user) {
        user = super.update(userId, user);

        String sql = "UPDATE User SET name = ?, email = ?, password = ?, role = ?, restaurantId = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole().name());
            stmt.setString(5, user.getRestaurantId() == null ? null : user.getRestaurantId());
            stmt.setString(6, userId);
            stmt.executeUpdate();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(User user) {
        super.delete(user);

        String sql = "DELETE FROM User WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(user.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);

        String sql = "DELETE FROM User WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(id));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        super.deleteAll();

        String sql = "DELETE FROM User";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
