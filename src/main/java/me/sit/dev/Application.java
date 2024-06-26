package me.sit.dev;

import me.sit.dev.repository.DatabaseConnection;
import me.sit.dev.repository.type.RepositoryType;
import me.sit.dev.service.ServiceFactory;
import me.sit.dev.ui.impl.AdminUI;
import me.sit.dev.ui.impl.ClientUI;
import me.sit.dev.ui.impl.LoginUI;
import me.sit.dev.ui.impl.RestaurantUI;

import java.io.*;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {

    public static ServiceFactory serviceFactory;
    public static void main(String[] args) {
        Config.load();

        if(Config.repositoryType == RepositoryType.DATABASE) {
            DatabaseConnection.createTables();
        }

        serviceFactory = new ServiceFactory(Config.repositoryType);

        ClientUI clientUI = new ClientUI(serviceFactory);
        RestaurantUI restaurantUI = new RestaurantUI(serviceFactory);
        AdminUI adminUI = new AdminUI(serviceFactory);

        LoginUI loginUI = new LoginUI(clientUI, restaurantUI, serviceFactory, adminUI);

        clientUI.setLoginUI(loginUI);
        restaurantUI.setLoginUI(loginUI);
        adminUI.setLoginUI(loginUI);
        loginUI.show();
    }

    public static ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public class Config {
        public static String DB_URL;
        public static String USER;
        public static String PASS;
        public static String JDBC_DRIVER;
        public static RepositoryType repositoryType;

        public static void load() {
            try (InputStream input = new BufferedInputStream(new FileInputStream("config/config.properties"));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

                Map<String, String> propertiesMap = reader.lines()
                        .filter(line -> !line.startsWith("#") && line.contains("="))
                        .map(line -> line.split("=", 2))
                        .collect(Collectors.toMap(arr -> arr[0].trim(), arr -> arr[1].trim()));

                // Get the property values
                DB_URL = propertiesMap.get("jdbc.url");
                USER = propertiesMap.get("jdbc.user");
                PASS = propertiesMap.get("jdbc.password");
                JDBC_DRIVER = propertiesMap.get("jdbc.driver");
                repositoryType = RepositoryType.valueOf(propertiesMap.get("repository.type"));
                System.out.println("Repository type: " + repositoryType);
            } catch (IOException ex) {
                throw new RuntimeException("Failed to load properties or JDBC driver!", ex);
            }
        }

    }
}
