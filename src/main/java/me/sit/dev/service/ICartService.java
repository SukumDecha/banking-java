package me.sit.dev.service;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.user.User;

public interface ICartService {

    boolean addToCart(User user, Restaurant restaurant, Product product, int quantity);

    boolean removeFromCart(User user, Product product);

    boolean updateCart(User user, Product product, int quantity);

    boolean existsInCart(User user, Product product);

    void clearCart(User user);

    void showCartDetails(User user);
}
