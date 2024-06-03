package me.sit.dev.entity.impl;

import me.sit.dev.entity.BaseEntity;
import me.sit.dev.service.UtilityService;

import java.util.HashMap;
import java.util.Map;

public class Cart extends BaseEntity {
    private final Map<Product, Integer> products = new HashMap<>();
    private Restaurant restaurant = null;

    public Cart() {
        super(UtilityService.generateId());
    }

    public void addProduct(Product product, int quantity) {
        products.put(product, quantity);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void updateProductQuantity(Product product, int quantity) {
        products.put(product, quantity);
    }

    public void clearCart() {
        products.clear();
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }
}

