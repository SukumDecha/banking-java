package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.exceptions.restaurant.RestaurantNotFoundException;
import me.sit.dev.repository.IRestaurantRepo;
import me.sit.dev.service.IRestaurantService;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantService implements IRestaurantService {

    private final IRestaurantRepo restaurantRepository;

    public RestaurantService(IRestaurantRepo restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public IRestaurantRepo getRestaurantRepository() {
        return restaurantRepository;
    }

    /**
     * Adds a new restaurant with the given owner ID and restaurant name.
     *
     * @param ownerId        the ID of the restaurant owner
     * @param restaurantName the name of the restaurant
     * @return the added Restaurant object
     */
    @Override
    public Restaurant addRestaurant(String ownerId, String restaurantName) {
        return restaurantRepository.addRestaurant(ownerId, restaurantName);
    }

    /**
     * Updates an existing restaurant with the given ID using the provided restaurant details.
     *
     * @param id         the ID of the restaurant to update
     * @param restaurant the updated Restaurant object
     * @return null if the restaurant was not successfully updated)
     */
    @Override
    public Restaurant updateRestaurant(String id, Restaurant restaurant) {
        return restaurantRepository.updateRestaurant(id, restaurant);
    }

    /**
     * Deletes the restaurant with the given ID.
     *
     * @param id the ID of the restaurant to delete
     * @return null if the restaurant was not successfully deleted)
     */
    @Override
    public Restaurant deleteRestaurant(String id) {
        return restaurantRepository.deleteRestaurant(id);
    }

    /**
     * Finds a restaurant by its ID.
     *
     * @param id the ID of the restaurant to find
     * @return null if the restaurant was not found)
     */
    @Override
    public Restaurant findById(String id) {
        return restaurantRepository.findById(id);
    }

    /**
     * Finds a restaurant by its name.
     *
     * @param name the name of the restaurant to find
     * @return null if the restaurant was not found)
     */
    @Override
    public Restaurant findByName(String name) {
        return restaurantRepository.findByName(name);
    }

    /**
     * Finds a restaurant by the owner's ID.
     *
     * @param ownerId the ID of the restaurant owner
     * @return null if the restaurant was not found)
     */
    @Override
    public Restaurant findByOwnerId(String ownerId) {
        return restaurantRepository.findByOwnerId(ownerId);
    }

    /**
     * Finds a restaurant by a product's ID.
     *
     * @param productId the ID of the product
     * @return null if the restaurant was not found)
     */
    @Override
    public Restaurant findByProduct(String productId) {
        return restaurantRepository.findByProduct(productId);
    }

    /**
     * Retrieves all restaurants.
     *
     * @return null if no restaurants were found)
     */
    @Override
    public Collection<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    /**
     * Finds restaurants by their rating.
     *
     * @param rating the rating of the restaurants to find
     * @return null if no restaurants were found)
     */
    @Override
    public Collection<Restaurant> findByRating(int rating) {
        return restaurantRepository.findByRating(rating);
    }

    /**
     * Checks if a restaurant with the given ID exists.
     *
     * @param id the ID of the restaurant to check
     * @return false if the restaurant does not exist)
     */
    @Override
    public boolean existsById(String id) {
        return restaurantRepository.existsById(id);
    }

    /**
     * Finds the top restaurants, limited by the specified number.
     *
     * @param limit the maximum number of top restaurants to return
     * @return null if no restaurants were found)
     */
    @Override
    public Collection<Restaurant> findTopRestaurants(int limit) {
        return findAll().stream().sorted().limit(limit).collect(Collectors.toList());
    }

    @Override
    public void showAllProducts(String restaurantId) {
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID cannot be null or empty");
        }

        Restaurant restaurant = findById(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        }

        List<Product> products = restaurant.getProducts();
        if (products.isEmpty()) {
            System.out.println("No products available for restaurant: " + restaurant.getName());
        } else {
            System.out.println("Products available for restaurant: " + restaurant.getName());
            System.out.println("------------------------------------------------");
            System.out.println("Product ID  | Name    |  Quantity   | Price");
            System.out.println("------------------------------------------------");
            products.forEach(product -> {
                System.out.printf("%-11s | %-7s | %-12d| %-6f %n", product.getId(), product.getName(), product.getQuantity(), product.getPrice());
            });
        }
    }

    @Override
    public int showOrderPagination(String restaurantId, int page, int size) {
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID cannot be null or empty");
        }

        Restaurant restaurant = findById(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        }

        int totalPage = (int) Math.ceil((double) restaurant.getOrders().size() / size);

        Collection<Order> orders = restaurant.getOrders().stream().sorted()
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());

        if(orders.isEmpty()) {
            System.out.println("No orders available for restaurant: " + restaurant.getName());
            return 0;
        }

        int count = page == 1 ? 1 : (page - 1) * size + 1;
        for (Order order : orders) {
            System.out.printf("--------- Order #%d  ---------%n", count);
            System.out.println("Order ID: " + order.getId());
            System.out.println("Order Status: " + order.getStatus());
            System.out.println("Order Date: " + new Date(order.getOrderAt()));
            if(order.getProducts().isEmpty()) {
                System.out.println("No products in this order");
                System.out.println("---------------------------");
                count++;
                continue;
            }
            System.out.println("Products:");
            for (Product product : order.getProducts().keySet()) {
                System.out.println("- " + product.getName() + " x" + order.getProducts().get(product));
            }
            System.out.println("Total Price: " + order.getTotalPrice());
            System.out.println("---------------------------");
            count++;
        }

        System.out.println("Current page: " + page + " / " + totalPage);

        return totalPage;
    }
}