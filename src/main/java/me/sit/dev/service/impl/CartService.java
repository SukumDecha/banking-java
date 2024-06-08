package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Cart;
import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.Session;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.exceptions.InvalidInputException;
import me.sit.dev.service.ICartService;

import java.util.HashMap;
import java.util.Map;

public class CartService implements ICartService {
    /**
     * Adds a specified quantity of a product to the user's cart for a specific restaurant.
     *
     * @param user       the user adding the product to the cart
     * @param restaurant the restaurant the product belongs to
     * @param product    the product to be added to the cart
     * @param quantity   the quantity of the product to be added
     * @return false the product was not successfully added
     */
    @Override
    public boolean addToCart(User user, Restaurant restaurant, Product product, int quantity) {
        if (user == null || product == null || quantity <= 0) {
            throw new InvalidInputException();
        }

        Cart cart = user.getCart();
        cart.setRestaurant(restaurant);
        cart.addProduct(product, quantity);

        Session.getCurrentSession().setRestaurantId(restaurant.getId());
        return true;
    }

    /**
     * Removes a product from the user's cart.
     *
     * @param user    the user removing the product from the cart
     * @param product the product to be removed from the cart
     * @return false the product was not successfully removed
     */
    @Override
    public boolean removeFromCart(User user, Product product) {
        if (user == null || product == null) {
            throw new InvalidInputException();
        }

        user.getCart().removeProduct(product);
        return true;
    }

    /**
     * Updates the quantity of a product in the user's cart.
     *
     * @param user     the user updating the product quantity in the cart
     * @param product  the product to update in the cart
     * @param quantity the new quantity of the product
     * @return false the product quantity was not successfully updated
     */
    @Override
    public boolean updateCart(User user, Product product, int quantity) {
        if (user == null || product == null || quantity <= 0) {
            throw new InvalidInputException();
        }

        Cart cart = user.getCart();
        cart.updateProductQuantity(product, quantity);
        return true;
    }

    /**
     * Checks if a product exists in the user's cart.
     *
     * @param user    the user whose cart is being checked
     * @param product the product to check for in the cart
     * @return false the product does not exist in the cart
     */
    @Override
    public boolean existsInCart(User user, Product product) {
        if (user == null || product == null) {
            throw new InvalidInputException();
        }

        return user.getCart().getProducts().get(product) != null;

    }

    public int getProductQuantity(User user, Product product) {
        if (user == null || product == null) {
            throw new InvalidInputException();
        }

        Map<Product, Integer> products = user.getCart().getProducts();
        return products.getOrDefault(product, 0);
    }

    /**
     * Clears all products from the user's cart.
     *
     * @param user the user whose cart will be cleared
     */
    @Override
    public void clearCart(User user) {
        user.getCart().setRestaurant(null);
        user.getCart().clearCart();
    }

    /**
     * Displays the details of all products in the user's cart.
     *
     * @param user the user whose cart details will be displayed
     */
    @Override
    public void showCartDetails(User user) {
        if (user == null) {
            System.out.println("Cart is empty or user does not exist.");
            return;
        }

        Cart userCart = user.getCart();
        System.out.println("Cart details for user: " + user.getName());
        userCart.getProducts().forEach((product, quantity) -> {
            System.out.println("Product: " + product.getName() + ", Quantity: " + quantity);
        });
    }
}