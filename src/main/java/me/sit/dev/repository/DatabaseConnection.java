package me.sit.dev.repository;

import me.sit.dev.Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    static {
        try {
            // Load the JDBC driver
            Class.forName(Application.Config.JDBC_DRIVER);
            System.out.println("JDBC Driver loaded!");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Failed to load properties or JDBC driver!", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        String DB_URL = Application.Config.DB_URL;
        String USER = Application.Config.USER;
        String PASS = Application.Config.PASS;

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connection has been created!");
            return connection;
        } catch (SQLException e) {
            System.err.println("Cannot connect to the database!");
            throw e;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Failed to close the database connection!");
                e.printStackTrace();
            }
        }
    }

    public static void createTables() {
        String[] sqlStatements = {
                "CREATE TABLE IF NOT EXISTS User (" +
                        "id VARCHAR(10) PRIMARY KEY," +
                        "name VARCHAR(100)," +
                        "email VARCHAR(100) UNIQUE," +
                        "password VARCHAR(100)," +
                        "role VARCHAR(100)," +
                        "restaurantId VARCHAR(10)" +
                        ")",

                "CREATE TABLE IF NOT EXISTS Restaurant (" +
                        "id VARCHAR(10) PRIMARY KEY," +
                        "ownerId VARCHAR(10)," +
                        "name VARCHAR(100)," +
                        "totalRating INT," +
                        "FOREIGN KEY (ownerId) REFERENCES User(id)" +
                        ")",

                "CREATE TABLE IF NOT EXISTS Product (" +
                        "id VARCHAR(10) PRIMARY KEY," +
                        "restaurantId VARCHAR(10)," +
                        "name VARCHAR(100)," +
                        "price DECIMAL(10, 2)," +
                        "quantity INT," +
                        "FOREIGN KEY (restaurantId) REFERENCES Restaurant(id)" +
                        ")",

                "CREATE TABLE IF NOT EXISTS CustomerOrder (" +
                        "id VARCHAR(20) PRIMARY KEY," +
                        "userId VARCHAR(10)," +
                        "orderAt BIGINT," +
                        "restaurantId VARCHAR(10)," +
                        "restaurantName VARCHAR(100)," +
                        "status VARCHAR(50)," +
                        "productMap TEXT," +
                        "FOREIGN KEY (userId) REFERENCES User(id)," +
                        "FOREIGN KEY (restaurantId) REFERENCES Restaurant(id)" +
                        ")"
        };

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            for (String sql : sqlStatements) {
                statement.executeUpdate(sql);
            }
            System.out.println("Database schema created successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create database schema!", e);
        }
    }
}
