package me.sit.dev.exceptions.user;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException() {
        super("User already exist");
    }

}
