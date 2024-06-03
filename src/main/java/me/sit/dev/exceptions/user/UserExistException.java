package me.sit.dev.exceptions.user;

public class UserExistException extends RuntimeException {

    public UserExistException() {
        super("User already exist");
    }

}
