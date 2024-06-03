package me.sit.dev.service;

import me.sit.dev.entity.impl.Product;

import java.util.List;

public interface IProductService {
    boolean addProduct(String productName, double price, int quantity);

    boolean updateProduct(String productId, Product product);

    boolean deleteProduct(String productId);

    Product findById(String productId);

    Product findByName(String productName);

    List<Product> findAll();

    boolean existsById(String productId);

    boolean existsByName(String productName);
}
