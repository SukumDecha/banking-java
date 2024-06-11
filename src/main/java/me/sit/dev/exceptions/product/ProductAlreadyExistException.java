package me.sit.dev.exceptions.product;

public class ProductAlreadyExistException extends RuntimeException {

        public ProductAlreadyExistException() {
            super("Product already exists");
        }

}
