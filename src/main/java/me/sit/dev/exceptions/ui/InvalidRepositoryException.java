package me.sit.dev.exceptions.ui;

public class InvalidRepositoryException extends RuntimeException {

    public InvalidRepositoryException() {
        super("Invalid repository type");
    }

    public InvalidRepositoryException(String message) {
        super(message);

    }
}