package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.Session;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.IProductRepo;
import me.sit.dev.service.IProductService;

import java.util.List;

public class ProductService implements IProductService {

    private final IProductRepo productRepo;

    public ProductService(IProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    /**
     * Adds a new product with the given name, price, and quantity to the product list.
     *
     * @param productName the name of the product
     * @param price the price of the product
     * @param quantity the quantity of the product
     * @return false if the product was not successfully added
     */
    @Override
    public boolean addProduct(Restaurant restaurant, String productName, double price, int quantity) {
        int currentId = findAll(restaurant).size() + 1;

        Product product = new Product(currentId, productName, price, quantity);

        findAll(restaurant).add(product);
        return false;
    }

    /**
     * Updates an existing product identified by the given product ID with the new product information.
     *
     * @param productId the ID of the product to update
     * @param product the new product information
     * @return false if the product was not successfully updated
     */
    @Override
    public boolean updateProduct(Restaurant restaurant, String productId, Product product) {
        return productRepo.updateProduct(restaurant, productId, product);
    }

    /**
     * Deletes a product identified by the given product ID from the product list.
     *
     * @param productId the ID of the product to delete
     * @return false indicating the product was not successfully deleted
     */
    @Override
    public boolean deleteProduct(Restaurant restaurant, String productId) {
        List<Product> productList = findAll(restaurant);
        for (Product product : productList) {
            if (product.getId().equals(productId)) {
                productList.remove(product);
                return true;
            }
        }
        return false;
    }

    /**
     * Finds a product by its ID.
     *
     * @param productId the ID of the product to find
     * @return the product with the given ID, or null if not found
     */
    @Override
    public Product findById(Restaurant restaurant, String productId) {
        List<Product> productList = findAll(restaurant);
        for (Product product : productList) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Finds a product by its name.
     *
     * @param productName the name of the product to find
     * @return the product with the given name, or null if not found
     */
    @Override
    public Product findByName(Restaurant restaurant, String productName) {
        List<Product> productList = findAll(restaurant);
        for (Product product : productList) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Retrieves all products for the current user's restaurant.
     *
     * @return a list of all products
     */
    @Override
    public List<Product> findAll(Restaurant restaurant) {
        User user = Session.getCurrentSession().getUser();
        return user.getRestaurant().getProducts();
    }

    /**
     * Displays all products in the restaurant.
     */
    @Override
    public void showAllProducts(Restaurant restaurant) {
        //Print all products in the restaurant; (use findAll(restaurant) function)
        List<Product> productList = findAll(restaurant);
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    /**
     * Checks if a product with the given ID exists.
     *
     * @param productId the ID of the product to check
     * @return false indicating the product does not exist
     */
    @Override
    public boolean existsById(Restaurant restaurant, String productId) {
        return findById(restaurant, productId) != null;
    }

    /**
     * Checks if a product with the given name exists.
     *
     * @param productName the name of the product to check
     * @return false indicating the product does not exist
     */
    @Override
    public boolean existsByName(Restaurant restaurant, String productName) {
        return findByName(restaurant, productName) != null;
    }
}
