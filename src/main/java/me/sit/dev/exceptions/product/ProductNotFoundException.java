package me.sit.dev.exceptions.product;

public class ProductNotFoundException extends RuntimeException {

        public ProductNotFoundException() {
            super("Product not found");
        }
}
