package me.sit.dev.service;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.repository.IRestaurantRepo;

import java.util.Collection;

public interface IRestaurantService extends IRestaurantRepo {

    void showAllProducts(String restaurantId);
    Collection<Restaurant> findTopRestaurants(int limit);

}
