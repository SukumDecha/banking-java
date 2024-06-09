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
    public Product updateProduct(String restaurantId, String productId, Product updatedProduct) {
        if (!productMap.containsKey(productId)) {
            throw new InvalidInputException("Product not found");
        }
        productMap.put(productId, updatedProduct);
        return updatedProduct;
    }

    @Override
    public Product deleteProduct(String restaurantId, String productId) {
        return productMap.remove(productId);
    }

    @Override
    public Product findById(String restaurantId, String productId) {
        return productMap.get(productId);
    }

    @Override
    public Product findByName(String restaurantId, String productName) {
        return productMap.values().stream()
                .filter(product -> product.getRestaurantId().equals(restaurantId) && product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Product> findAll(String restaurantId) {
        return productMap.values().stream()
                .filter(product -> product.getRestaurantId().equals(restaurantId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById( String productId) {
        return productMap.containsKey(productId);
    }

    @Override
    public boolean existsByName(String restaurantId, String productName) {
        return productMap.values().stream()
                .anyMatch(product -> product.getRestaurantId().equals(restaurantId) && product.getName().equals(productName));
    }
}
