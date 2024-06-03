package me.sit.dev.exceptions.restaurant;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException() {
        super("Restaurant not found");
    }
}
