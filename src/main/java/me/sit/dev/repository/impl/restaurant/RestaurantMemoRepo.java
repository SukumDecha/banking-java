package me.sit.dev.repository.impl.restaurant;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.exceptions.restaurant.RestaurantNotFoundException;
import me.sit.dev.repository.IRestaurantRepo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RestaurantMemoRepo implements IRestaurantRepo {
    private final Map<String,Restaurant> restaurantMap = new HashMap<>();
    @Override
    public Restaurant addRestaurant(String ownerId, String restaurantName) {

        return null;
    }

    @Override
    public Restaurant updateRestaurant(String id, Restaurant restaurant) {
        if (!restaurantMap.containsKey(id)) {
            throw new RestaurantNotFoundException();
        }
        restaurantMap.put(id, restaurant);
        return restaurant;
    }

    @Override
    public Restaurant deleteRestaurant(String id) {
        if (!restaurantMap.containsKey(id)) {
            throw new RestaurantNotFoundException();
        }
        return restaurantMap.remove(id);
    }

    @Override
    public Restaurant findById(String id) {
        return null;
    }

    @Override
    public Restaurant findByName(String name) {
        return null;
    }

    @Override
    public Restaurant findByOwnerId(String ownerId) {
        return null;
    }

    @Override
    public Restaurant findByProduct(String productId) {
        return null;
    }

    @Override
    public Collection<Restaurant> findAll() {
        return null;
    }

    @Override
    public Collection<Restaurant> findByRating(int rating) {
        return null;
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }
}
