package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.repository.IProductRepo;
import me.sit.dev.repository.IRestaurantRepo;
import me.sit.dev.service.IProductService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductService implements IProductService {

    private final IRestaurantRepo restaurantRepo;
    private final IProductRepo productRepo;

    public ProductService(IProductRepo productRepo, IRestaurantRepo restaurantRepo) {
        this.productRepo = productRepo;
        this.restaurantRepo = restaurantRepo;
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

    @Override
    public int getQuantity(String restaurantId, String productId) {
        return findAll(restaurantId).stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .map(Product::getQuantity)
                .orElse(0);
    }

    @Override
    public List<Product> searchByName(String productName) {
        Collection<Restaurant> restaurants = restaurantRepo.findAll();
        List<Product> products = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            for(Product product : restaurant.getProducts()) {
                boolean match = product.getName().toLowerCase().contains(productName.toLowerCase());
                if (match) {
                    products.add(product);
                }
            }
        }

        return products;

    }
}