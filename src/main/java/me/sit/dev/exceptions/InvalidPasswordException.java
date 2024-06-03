package me.sit.dev.exceptions;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Invalid password");
    }
}
