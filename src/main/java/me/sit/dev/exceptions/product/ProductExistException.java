package me.sit.dev.exceptions.product;

public class ProductExistException extends RuntimeException {

        public ProductExistException() {
            super("Product already exists");
        }
}
