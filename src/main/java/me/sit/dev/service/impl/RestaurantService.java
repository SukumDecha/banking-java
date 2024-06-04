package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.repository.IRestaurantRepo;
import me.sit.dev.service.IRestaurantService;

import java.util.Collection;
import java.util.Comparator;

public class RestaurantService implements IRestaurantService {

    private final IRestaurantRepo restaurantRepository;

    public RestaurantService(IRestaurantRepo restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant addRestaurant(String ownerId, String restaurantName) {
        return null;
    }

    @Override
    public Restaurant updateRestaurant(String id, Restaurant restaurant) {
        return null;
    }

    @Override
    public Restaurant deleteRestaurant(String id) {
        return null;
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

    @Override
    public Collection<Restaurant> findTopRestaurants(int limit) {
        return null;
    }
}
