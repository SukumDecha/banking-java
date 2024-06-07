package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.service.ICartService;

public class CartService implements ICartService {

    /**
     * Adds a specified quantity of a product to the user's cart for a specific restaurant.
     *
     * @param user the user adding the product to the cart
     * @param restaurant the restaurant the product belongs to
     * @param product the product to be added to the cart
     * @param quantity the quantity of the product to be added
     * @return false the product was not successfully added
     */
    @Override
    public boolean addToCart(User user, Restaurant restaurant, Product product, int quantity) {
        return false;
    }

    /**
     * Removes a product from the user's cart.
     *
     * @param user the user removing the product from the cart
     * @param product the product to be removed from the cart
     * @return false the product was not successfully removed
     */
    @Override
    public boolean removeFromCart(User user, Product product) {
        return false;
    }

    /**
     * Updates the quantity of a product in the user's cart.
     *
     * @param user the user updating the product quantity in the cart
     * @param product the product to update in the cart
     * @param quantity the new quantity of the product
     * @return false the product quantity was not successfully updated
     */
    @Override
    public boolean updateCart(User user, Product product, int quantity) {
        return false;
    }

    /**
     * Checks if a product exists in the user's cart.
     *
     * @param user the user whose cart is being checked
     * @param product the product to check for in the cart
     * @return false the product does not exist in the cart
     */
    @Override
    public boolean existsInCart(User user, Product product) {
        return false;
    }

    /**
     * Clears all products from the user's cart.
     *
     * @param user the user whose cart will be cleared
     */
    @Override
    public void clearCart(User user) {
        // Clear all products from the user's cart (implementation needed)
    }

    /**
     * Displays the details of all products in the user's cart.
     *
     * @param user the user whose cart details will be displayed
     */
    @Override
    public void showCartDetails(User user) {
        // Display details of all products in the user's cart (implementation needed)
    }
}
