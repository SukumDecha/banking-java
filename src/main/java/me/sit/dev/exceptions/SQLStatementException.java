package me.sit.dev.exceptions;

public class SQLStatementException extends RuntimeException {
    public SQLStatementException() {
        super("SQL Statement Exception");
    }

    public SQLStatementException(String message) {
        super(message);
    }
}
