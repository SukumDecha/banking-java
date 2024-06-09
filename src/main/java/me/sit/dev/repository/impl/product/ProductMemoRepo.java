package me.sit.dev.repository.impl.product;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.exceptions.InvalidInputException;
import me.sit.dev.exceptions.product.ProductAlreadyExistException;
import me.sit.dev.repository.IProductRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductMemoRepo implements IProductRepo {

    protected final Map<String, Product> productMap;

    public ProductMemoRepo() {
        productMap = new HashMap<>();
    }

    @Override
    public Product addProduct(String restaurantId, String productName, double price, int quantity) {
        if (restaurantId == null || restaurantId.isBlank() || productName == null || productName.isBlank()) {
            throw new InvalidInputException("Invalid input");
        }

        Product product = findByName(restaurantId, productName);
        if (product != null) {
            throw new ProductAlreadyExistException();
        }

        String productId = restaurantId + "-" + (findAll(restaurantId).size() + 1);
        product = new Product(productId, restaurantId, productName, price, quantity);

        productMap.put(product.getId(), product);
        return product;
    }

    @Override
    public Product updateProduct(String productId, Product updatedProduct) {
        if(updatedProduct == null) {
            throw new InvalidInputException("Updated product is null");
        }
        if(productId == null || productId.isBlank()) {
            throw new InvalidInputException("Product id is null or empty");
        }

        if (!existsById(productId)) {
            Product product = findById(productId);
            System.out.println(product);
            throw new InvalidInputException("Product not found");
        }

        productMap.replace(productId, updatedProduct);
        return updatedProduct;
    }

    @Override
    public Product deleteProduct(String restaurantId, String productId) {
        return productMap.remove(productId);
    }

    @Override
    public Product findById(String productId) {
        if(productId == null || productId.isBlank()) {
            throw new InvalidInputException("Product id is null or empty");
        }

        return productMap.get(productId);
    }

    @Override
    public Product findByName(String restaurantId, String productName)
    {
        if(restaurantId == null || restaurantId.isBlank() || productName == null || productName.isBlank()) {
            throw new InvalidInputException("Invalid input");
        }
        return productMap.values().stream()
                .filter(product -> product.getRestaurantId().equals(restaurantId) && product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Product> findAll(String restaurantId) {
        if(restaurantId == null || restaurantId.isBlank()) {
            throw new InvalidInputException("Invalid input");
        }
        return productMap.values().stream()
                .filter(product -> product.getRestaurantId().equals(restaurantId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String productId) {
        if(productId == null || productId.isBlank()) {
            throw new InvalidInputException("Product id is null or empty");
        }
        return productMap.containsKey(productId);
    }

    @Override
    public boolean existsByName(String restaurantId, String productName) {
        if(restaurantId == null || restaurantId.isBlank() || productName == null || productName.isBlank()) {
            throw new InvalidInputException("Invalid input");
        }
        return productMap.values().stream()
                .anyMatch(product -> product.getRestaurantId().equals(restaurantId) && product.getName().equals(productName));
    }
}
