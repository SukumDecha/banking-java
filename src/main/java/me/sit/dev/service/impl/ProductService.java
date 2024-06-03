package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.Session;
import me.sit.dev.service.IProductService;

import java.util.List;

public class ProductService implements IProductService {
    @Override
    public boolean addProduct(String productName, double price, int quantity) {
        if (existsByName(productName)) {
            return false;
        }

        Product product = new Product(productName, price, quantity);
        findAll().add(product);
        return true;
    }

    @Override
    public boolean updateProduct(String productId, Product product) {
        for (Product p : findAll()) {
            if (p.getId().equals(productId)) {
                p.setName(product.getName());
                p.setPrice(product.getPrice());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteProduct(String productId) {
        for (Product p : findAll()) {
            if (p.getId().equals(productId)) {
                findAll().remove(p);
                return true;
            }
        }
        return false;
    }

    @Override
    public Product findById(String productId) {
        for (Product p : findAll()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public Product findByName(String productName) {
        for (Product p : findAll()) {
            if (p.getName().equals(productName)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        return Session.getCurrentSession().getUser().getRestaurant().getProducts();
    }

    @Override
    public boolean existsById(String productId) {
        for (Product p : findAll()) {
            if (p.getId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsByName(String productName) {
        for (Product p : findAll()) {
            if (p.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }


}
