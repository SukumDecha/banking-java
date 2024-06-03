package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.service.ICartService;

public class CartService implements ICartService {
    @Override
    public boolean addToCart(User user, Restaurant restaurant, Product product, int quantity) {
        if(existsInCart(user, product)) {
            updateCart(user, product, quantity);
            return false;
        }

        user.getCart().getProducts().put(product, quantity);
        return true;
    }

    @Override
    public boolean removeFromCart(User user, Product product) {
        if(!existsInCart(user, product)) {
            return false;
        }

        user.getCart().getProducts().remove(product);
        return true;
    }

    @Override
    public boolean updateCart(User user, Product product, int quantity) {
        user.getCart().getProducts().replace(product, quantity);
        return true;
    }

    @Override
    public boolean existsInCart(User user, Product product) {
        return user.getCart().getProducts().containsKey(product);
    }

    @Override
    public void clearCart(User user) {
        user.getCart().getProducts().clear();
    }

    @Override
    public void showCartDetails(User user) {
        System.out.println("--- Cart details ---");
        System.out.println("User: " + user.getName());
        System.out.println("Restaurant: " + user.getCart().getRestaurant().getName());
        System.out.println("Products: ");
        user.getCart().getProducts().forEach((product, quantity) -> {
            System.out.println("Product: " + product.getName() + ", Quantity: " + quantity);
        });
        System.out.println("Total Price: " + user.getCart().getProducts().entrySet().stream().mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum());
        System.out.println("--------------------");
    }
}
