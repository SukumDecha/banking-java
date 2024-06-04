package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.service.ICartService;

public class CartService implements ICartService {

    @Override
    public boolean addToCart(User user, Restaurant restaurant, Product product, int quantity) {
        return false;
    }

    @Override
    public boolean removeFromCart(User user, Product product) {
        return false;
    }

    @Override
    public boolean updateCart(User user, Product product, int quantity) {
        return false;
    }

    @Override
    public boolean existsInCart(User user, Product product) {
        return false;
    }

    @Override
    public void clearCart(User user) {

    }

    @Override
    public void showCartDetails(User user) {
    }
}
