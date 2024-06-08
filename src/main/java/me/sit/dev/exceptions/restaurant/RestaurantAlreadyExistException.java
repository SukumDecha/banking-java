package me.sit.dev.exceptions.restaurant;

public class RestaurantAlreadyExistException extends RuntimeException {

    public RestaurantAlreadyExistException() {
        super("Restaurant already exists");
    }

}
