package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.exceptions.restaurant.RestaurantNotFoundException;
import me.sit.dev.repository.IRestaurantRepo;
import me.sit.dev.service.IRestaurantService;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantService implements IRestaurantService {

    private final IRestaurantRepo restaurantRepository;

    public RestaurantService(IRestaurantRepo restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
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
        return findAll().stream().sorted(new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                return (int) (o2.getRating() - o1.getRating());
            }
        }).limit(limit).collect(Collectors.toList());
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
            System.out.println("Product ID  | Name    |  Quantity   | Price");
            products.forEach(product -> {
                System.out.println(String.format("%-11s | %-7s | %-12d| %-6f ",product.getId(),product.getName(),product.getQuantity(),product.getPrice()));
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

        Collection<Order> orders = restaurant.getOrders().stream().sorted()
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());

        orders.forEach(order -> {
            System.out.println("-------------------------");
            System.out.println("Order ID: " + order.getId());
            System.out.println("Owner ID: " + order.getOwnerId());
            System.out.println("Restaurant ID: " + order.getRestaurantId());
            System.out.println("Restaurant Name: " + order.getRestaurantName());
            System.out.println("Order Status: " + order.getStatus());
            System.out.println("Order Total Price: " + order.getTotalPrice());
            System.out.println("Order Time: " + new Date(order.getOrderAt()));
            System.out.println("-------------------------");
        });

        return orders.size() / size + 1;
    }
}