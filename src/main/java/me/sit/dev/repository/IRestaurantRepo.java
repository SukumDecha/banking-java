package me.sit.dev.repository;

import me.sit.dev.entity.impl.Restaurant;

import java.util.Collection;

public interface IRestaurantRepo {

    Restaurant addRestaurant(String ownerId, String restaurantName);

    Restaurant updateRestaurant(String id, Restaurant restaurant);

    Restaurant deleteRestaurant(String id);

    Restaurant findById(String id);

    Restaurant findByName(String name);

    Restaurant findByOwnerId(String ownerId);

    Restaurant findByProduct(String productId);

    Collection<Restaurant> findAll();

    Collection<Restaurant> findByRating(int rating);

    boolean existsById(String id);
}
