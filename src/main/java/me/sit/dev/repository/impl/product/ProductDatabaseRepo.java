package me.sit.dev.repository.impl.product;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.exceptions.SQLStatementException;
import me.sit.dev.repository.DatabaseConnection;
import me.sit.dev.repository.IProductRepo;
import me.sit.dev.repository.impl.restaurant.RestaurantDatabaseRepo;
import me.sit.dev.repository.impl.restaurant.RestaurantMemoRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDatabaseRepo extends ProductMemoRepo implements IProductRepo {

    private final Connection connection;

    public ProductDatabaseRepo() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }

        loadProductsFromDatabase();
    }

    private void loadProductsFromDatabase() {
        String sql = "SELECT * FROM Product";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                String restaurantId = rs.getString("restaurantId");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                Product product = new Product(id, restaurantId, name, price, quantity);
                productMap.put(id, product);
            }
        } catch (SQLException e) {
            System.out.println("Error loading products from database: " + e.getMessage());
        }
    }

    @Override
    public Product addProduct(String restaurantId, String productName, double price, int quantity) {
        Product product = super.addProduct(restaurantId, productName, price, quantity);

        String sql = "INSERT INTO Product (restaurantId, name, price, quantity, id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, restaurantId);
            stmt.setString(2, productName);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            stmt.setString(5, product.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding product to database: " + e.getMessage());
        }
        return product;
    }

    @Override
    public Product updateProduct(String productId, Product product) {
        product = super.updateProduct(productId, product);

        String sql = "UPDATE Product SET name = ?, price = ?, quantity = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setString(4, productId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows < 0) {
                throw new SQLStatementException("Update Product failed, no row affected");
            }
        } catch (SQLException e) {
            System.out.println("Error updating product in database: " + e.getMessage());
        }
        return product;
    }

    @Override
    public Product deleteProduct(String restaurantId, String productId) {
        Product product = super.deleteProduct(restaurantId, productId);

        String sql = "DELETE FROM Product WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, productId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows < 0) {
                throw new SQLStatementException("Delete Product failed, no row affected");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting product from database: " + e.getMessage());
        }

        return product;
    }
}
