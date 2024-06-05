package me.sit.dev.exceptions;

public class NullInputException extends RuntimeException {
    public NullInputException() {
        super("Null input Exception.");
    }

    public NullInputException(String message) {
        super(message);
    }

}
