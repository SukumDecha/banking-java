package me.sit.dev.entity.impl;

import me.sit.dev.entity.BaseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cart extends BaseEntity {
    private final Map<Product, Integer> products = new HashMap<>();
    private Restaurant restaurant = null;

    public Cart() {
        super("C-" + UUID.randomUUID());
    }

    public Cart clone() {
        Cart cart = new Cart();
        cart.products.putAll(products);
        cart.restaurant = restaurant;

        return cart;
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

