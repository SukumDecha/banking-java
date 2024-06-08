package me.sit.dev.repository;


import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;

import java.util.List;

public interface IProductRepo {
    Product addProduct(String restaurantId, String productName, double price, int quantity);

    Product updateProduct(String restaurantId, String productId, Product updatedProduct);

    Product deleteProduct(String restaurantId, String productId);

    Product findById(String restaurantId, String productId);

    Product findByName(String restaurantId, String productName);

    List<Product> findAll(String restaurantId);

    boolean existsById(String productId);

    boolean existsByName(String restaurantId, String productName);
}
