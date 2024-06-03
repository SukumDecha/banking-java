package me.sit.dev.exceptions.restaurant;

public class RestaurantExistException extends RuntimeException {

    public RestaurantExistException() {
        super("Restaurant already exists");
    }

}
