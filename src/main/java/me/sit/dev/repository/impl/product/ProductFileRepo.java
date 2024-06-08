package me.sit.dev.repository.impl.product;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.user.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ProductFileRepo extends ProductMemoRepo {
    private final String path = "src/main/resources/products/";
    protected final Map<Restaurant, Product> productMap = new HashMap<>();


    @Override
    public boolean addProduct(Restaurant restaurant, String productName, double price, int quantity) {
        boolean result = super.addProduct(restaurant, productName, price, quantity);
        if (result) {
            String fileName = path + restaurant.getName()+ "-" + productName + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(productName + "," + price + "," + quantity);
                writer.newLine();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean updateProduct(Restaurant restaurant, String productId, Product product) {
        return super.updateProduct(restaurant, productId, product);
    }

    @Override
    public boolean deleteProduct(Restaurant restaurant, String productId) {
        return super.deleteProduct(restaurant, productId);
    }
    private File getFileFromProduct(Product product) {
        return new File(path + product.getName() + "-" + product.getId() + ".txt");
    }

}
