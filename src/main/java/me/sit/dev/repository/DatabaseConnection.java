package me.sit.dev.repository;

import me.sit.dev.Application;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

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
            System.out.println("Database connected!");
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
}
