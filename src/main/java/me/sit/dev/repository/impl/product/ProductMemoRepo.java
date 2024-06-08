package me.sit.dev.repository.impl.product;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.repository.IProductRepo;

import java.util.List;

public class ProductMemoRepo implements IProductRepo  {

    @Override
    public boolean addProduct(Restaurant restaurant, String productName, double price, int quantity) {
        return false;
    }

    @Override
    public boolean updateProduct(Restaurant restaurant, String productId, Product product) {
        return false;
    }

    @Override
    public boolean deleteProduct(Restaurant restaurant, String productId) {
        return false;
    }

    @Override
    public Product findById(Restaurant restaurant, String productId) {
        return null;
    }

    @Override
    public Product findByName(Restaurant restaurant, String productName) {
        return null;
    }

    @Override
    public List<Product> findAll(Restaurant restaurant) {
        return null;
    }

    @Override
    public boolean existsById(Restaurant restaurant, String productId) {
        return false;
    }

    @Override
    public boolean existsByName(Restaurant restaurant, String productName) {
        return false;
    }
}
