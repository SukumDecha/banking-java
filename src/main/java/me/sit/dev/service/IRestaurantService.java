package me.sit.dev.service;

import me.sit.dev.entity.impl.Restaurant;

import java.util.Collection;

public interface IRestaurantService {
    Restaurant addRestaurant(String ownerId, String restaurantName);

    Restaurant updateRestaurant(String id, Restaurant restaurant);

    Restaurant deleteRestaurant(String id);

    Restaurant findById(String id);

    Restaurant findByName(String name);

    Restaurant findByOwnerId(String ownerId);

    Restaurant findByProduct(String productId);

    Collection<Restaurant> findAll();

    Collection<Restaurant> findByRating(int rating);

    Collection<Restaurant> findTopRestaurants(int limit);

    boolean existsById(String id);
}
