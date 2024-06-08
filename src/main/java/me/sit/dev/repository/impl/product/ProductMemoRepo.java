package me.sit.dev.repository.impl.product;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.exceptions.InvalidInputException;
import me.sit.dev.exceptions.product.ProductExistException;
import me.sit.dev.repository.IProductRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductMemoRepo implements IProductRepo  {

    protected final Map<String, Product> productMap = new HashMap<>();

    @Override
    public Product addProduct(String restaurantId, String productName, double price, int quantity) {
        if(restaurantId == null || restaurantId.isBlank() || productName == null || productName.isBlank()) {
            throw new InvalidInputException();
        }

        Product product = findByName(restaurantId, productName);
        if(product != null) {
            throw new ProductExistException();
        }

        int currentSize = findAll(restaurantId).size() + 1;
        product = new Product(currentSize, restaurantId, productName, price, quantity);

        productMap.put(product.getId(), product);
        return product;
    }

    @Override
    public Product updateProduct(String restaurantId, String productId, Product product) {
        return null;
    }

    @Override
    public Product deleteProduct(String restaurantId, String productId) {
        return null;
    }

    @Override
    public Product findById(String restaurantId, String productId) {
        return null;
    }

    @Override
    public Product findByName(String restaurantId, String productName) {
        return null;
    }

    @Override
    public List<Product> findAll(String restaurantId) {
        return null;
    }

    @Override
    public boolean existsById(String restaurantId, String productId) {
        return false;
    }

    @Override
    public boolean existsByName(String restaurantId, String productName) {
        return false;
    }
}
