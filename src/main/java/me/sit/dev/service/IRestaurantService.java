package me.sit.dev.service;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.repository.IRestaurantRepo;

import java.util.Collection;

public interface IRestaurantService extends IRestaurantRepo {

    Collection<Restaurant> findTopRestaurants(int limit);

}
