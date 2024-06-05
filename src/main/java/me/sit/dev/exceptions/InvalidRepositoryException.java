package me.sit.dev.exceptions;

public class InvalidRepositoryException extends RuntimeException {

    public InvalidRepositoryException() {
        super("Invalid repository type");
    }

    public InvalidRepositoryException(String message) {
        super(message);
    }
}