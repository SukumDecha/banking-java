package me.sit.dev.repository.impl.product;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;

import java.util.List;

public class ProductFileRepo extends ProductMemoRepo {
    @Override
    public Product addProduct(String restaurantId, String productName, double price, int quantity) {
        return super.addProduct(restaurantId, productName, price, quantity);
    }

    @Override
    public Product updateProduct(String restaurantId, String productId, Product product) {
        return super.updateProduct(restaurantId, productId, product);
    }

    @Override
    public Product deleteProduct(String restaurantId, String productId) {
        return super.deleteProduct(restaurantId, productId);
    }

    @Override
    public Product findById(String restaurantId, String productId) {
        return super.findById(restaurantId, productId);
    }

    @Override
    public Product findByName(String restaurantId, String productName) {
        return super.findByName(restaurantId, productName);
    }

    @Override
    public List<Product> findAll(String restaurantId) {
        return super.findAll(restaurantId);
    }

    @Override
    public boolean existsById(String restaurantId, String productId) {
        return super.existsById(restaurantId, productId);
    }

    @Override
    public boolean existsByName(String restaurantId, String productName) {
        return super.existsByName(restaurantId, productName);
    }
}
