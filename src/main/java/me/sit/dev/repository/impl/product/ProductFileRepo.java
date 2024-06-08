package me.sit.dev.repository.impl.product;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;

import java.io.File;

public class ProductFileRepo extends ProductMemoRepo {
    private final String path = "src/main/resources/products/";

    @Override
    public boolean addProduct(Restaurant restaurant, String productName, double price, int quantity) {
        super.addProduct(restaurant, productName, price, quantity);
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
