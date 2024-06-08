package me.sit.dev.repository;


import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;

import java.util.List;

public interface IProductRepo {
    boolean addProduct(Restaurant restaurant, String productName, double price, int quantity);

    boolean updateProduct(Restaurant restaurant, String productId, Product product);

    boolean deleteProduct(Restaurant restaurant, String productId);

    Product findById(Restaurant restaurant, String productId);

    Product findByName(Restaurant restaurant, String productName);

    List<Product> findAll(Restaurant restaurant);

    boolean existsById(Restaurant restaurant, String productId);

    boolean existsByName(Restaurant restaurant, String productName);
}
