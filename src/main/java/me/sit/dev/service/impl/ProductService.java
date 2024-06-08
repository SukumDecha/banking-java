package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.Session;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.IProductRepo;
import me.sit.dev.service.IProductService;

import java.util.List;

public class ProductService implements IProductService {

    private final IProductRepo productRepo;

    public ProductService(IProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product addProduct(String restaurantId, String productName, double price, int quantity) {
        return null;
    }

    @Override
    public Product updateProduct(String restaurantId, String productId, Product product) {
        return null;
    }

    @Override
    public Product deleteProduct(String restaurantId, String productId) {
        return null;
    }

    @Override
    public Product findById(String restaurantId, String productId) {
        return null;
    }

    @Override
    public Product findByName(String restaurantId, String productName) {
        return null;
    }

    @Override
    public List<Product> findAll(String restaurantId) {
        return null;
    }

    @Override
    public boolean existsById(String restaurantId, String productId) {
        return false;
    }

    @Override
    public boolean existsByName(String restaurantId, String productName) {
        return false;
    }

    @Override
    public void showAllProducts(Restaurant restaurant) {

    }
}
