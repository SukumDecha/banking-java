package me.sit.dev.repository.impl.restaurant;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.exceptions.InvalidInputException;
import me.sit.dev.exceptions.restaurant.RestaurantExistException;
import me.sit.dev.exceptions.restaurant.RestaurantNotFoundException;
import me.sit.dev.repository.IRestaurantRepo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RestaurantMemoRepo implements IRestaurantRepo {
    private final Map<String, Restaurant> restaurantMap = new HashMap<>();

    @Override
    public Restaurant addRestaurant(String ownerId, String restaurantName) {
        Restaurant restaurant = findByName(restaurantName);
        if (restaurant != null) {
            throw new RestaurantExistException();
        }
        restaurant = new Restaurant(ownerId, restaurantName, 0);
        restaurantMap.put(restaurant.getId(), restaurant);
        return restaurant;
    }

    @Override
    public Restaurant updateRestaurant(String id, Restaurant restaurant) {
        if (restaurant == null || id == null || id.isBlank()) {
            throw new InvalidInputException();
        }
        if (!restaurantMap.containsKey(id)) {
            throw new RestaurantNotFoundException();
        }
        restaurantMap.put(id, restaurant);
        return restaurant;
    }

    @Override
    public Restaurant deleteRestaurant(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidInputException();
        }
        return restaurantMap.remove(id);
    }

    @Override
    public Restaurant findById(String id) {
        Restaurant restaurant = restaurantMap.get(id);
        if (restaurant == null) {
            throw new RestaurantNotFoundException();
        }
        return restaurant;

    }

    @Override
    public Restaurant findByName(String name) {
        return restaurantMap.values().stream()
                .filter(restaurant -> restaurant.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RestaurantNotFoundException());
    }


    @Override
    public Restaurant findByOwnerId(String ownerId) {
        return restaurantMap.values().stream()
                .filter(restaurant -> restaurant.getOwnerId().equals(ownerId))
                .findFirst()
                .orElseThrow(() -> new RestaurantNotFoundException());
    }

    @Override
    public Restaurant findByProduct(String productId) {
        // Assuming restaurants have products with IDs
        return restaurantMap.values().stream()
                .filter(restaurant -> restaurant.getProducts().contains(productId)) // Assuming getProducts method
                .findFirst()
                .orElseThrow(() -> new RestaurantNotFoundException());
    }

    @Override
    public Collection<Restaurant> findAll() {
        return restaurantMap.values();
    }

    @Override
    public Collection<Restaurant> findByRating(int rating) {
        return restaurantMap.values().stream()
                .filter(restaurant -> restaurant.getTotalRating() == rating) // Assuming getTotalRating method
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String id) {
        return restaurantMap.containsKey(id);
    }

}
