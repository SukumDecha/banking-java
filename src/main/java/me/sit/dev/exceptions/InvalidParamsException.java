package me.sit.dev.exceptions;

public class InvalidParamsException extends RuntimeException {

        public InvalidParamsException() {
            super("Invalid parameters");
        }

        public InvalidParamsException(String message) {
            super(message);
        }
}
