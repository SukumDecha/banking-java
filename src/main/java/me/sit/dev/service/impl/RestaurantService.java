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
        if(restaurantRepository.findByName(restaurantName) != null) {
            return null;
        }
        return restaurantRepository.addRestaurant(ownerId, restaurantName);
    }

    @Override
    public Restaurant updateRestaurant(String id, Restaurant restaurant) {
        return restaurantRepository.updateRestaurant(id, restaurant);
    }

    @Override
    public Restaurant deleteRestaurant(String id) {
        return restaurantRepository.deleteRestaurant(id);
    }

    @Override
    public Restaurant findById(String id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public Restaurant findByName(String name) {
        return restaurantRepository.findByName(name);
    }

    @Override
    public Restaurant findByOwnerId(String ownerId) {
        return restaurantRepository.findByOwnerId(ownerId);
    }

    @Override
    public Restaurant findByProduct(String productId) {
        return restaurantRepository.findByProduct(productId);
    }

    @Override
    public Collection<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    @Override
    public Collection<Restaurant> findByRating(int rating) {
        return restaurantRepository.findByRating(rating);
    }

    @Override
    public Collection<Restaurant> findTopRestaurants(int limit) {
        return findAll().stream().sorted(new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                return (int) (o2.getRating() - o1.getRating());
            }
        }).limit(limit).toList();
    }


    @Override
    public boolean existsById(String id) {
        return restaurantRepository.existsById(id);
    }

}
