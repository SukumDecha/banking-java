package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
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
        return productRepo.addProduct(restaurantId, productName, price, quantity);
    }

    @Override
    public Product updateProduct(String restaurantId, String productId, Product product) {
        return productRepo.updateProduct(restaurantId, productId, product);
    }

    @Override
    public Product deleteProduct(String restaurantId, String productId) {
        return productRepo.deleteProduct(restaurantId, productId);
    }

    @Override
    public Product findById(String restaurantId, String productId) {
        return productRepo.findById(restaurantId, productId);
    }

    @Override
    public Product findByName(String restaurantId, String productName) {
        return productRepo.findByName(restaurantId, productName);
    }

    @Override
    public List<Product> findAll(String restaurantId) {
        return productRepo.findAll(restaurantId);
    }

    @Override
    public boolean existsById(String productId) {
        return productRepo.existsById(productId);
    }

    @Override
    public boolean existsByName(String restaurantId, String productName) {
        return productRepo.existsByName(restaurantId, productName);
    }

}