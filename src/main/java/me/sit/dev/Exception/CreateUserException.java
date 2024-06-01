package me.sit.dev.Exception;

public class CreateUserException extends RuntimeException{
    public CreateUserException(String massage){
        super(massage);
    }

    public CreateUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateUserException(Throwable cause) {
        super(cause);
    }

    protected CreateUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
