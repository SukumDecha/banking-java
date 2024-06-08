package me.sit.dev.repository.impl.product;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;

import java.util.List;

public class ProductDatabaseRepo extends ProductMemoRepo {
    @Override
    public boolean addProduct(Restaurant restaurant, String productName, double price, int quantity) {
        return super.addProduct(restaurant, productName, price, quantity);
    }

    @Override
    public boolean updateProduct(Restaurant restaurant, String productId, Product product) {
        return super.updateProduct(restaurant, productId, product);
    }

    @Override
    public boolean deleteProduct(Restaurant restaurant, String productId) {
        return super.deleteProduct(restaurant, productId);
    }

    @Override
    public List<Product> findAll(Restaurant restaurant) {
        return super.findAll(restaurant);
    }
}
