package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.Session;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.service.IProductService;

import java.util.List;

public class ProductService implements IProductService {

    @Override
    public boolean addProduct(String productName, double price, int quantity) {
        Product product = new Product(productName, price, quantity);

        findAll().add(product);
        return false;
    }

    @Override
    public boolean updateProduct(String productId, Product product) {
        return false;
    }

    @Override
    public boolean deleteProduct(String productId) {
        return false;
    }

    @Override
    public Product findById(String productId) {
        return null;
    }

    @Override
    public Product findByName(String productName) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        User user = Session.getCurrentSession().getUser();

        return user.getRestaurant().getProducts();
    }

    @Override
    public void showAllProducts() {

    }

    @Override
    public boolean existsById(String productId) {
        return false;
    }

    @Override
    public boolean existsByName(String productName) {
        return false;
    }
}
