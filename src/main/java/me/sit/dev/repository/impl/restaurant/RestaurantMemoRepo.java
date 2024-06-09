package me.sit.dev.repository.impl.restaurant;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.exceptions.InvalidInputException;
import me.sit.dev.exceptions.InvalidParamsException;
import me.sit.dev.exceptions.restaurant.RestaurantAlreadyExistException;
import me.sit.dev.exceptions.restaurant.RestaurantNotFoundException;
import me.sit.dev.repository.IRestaurantRepo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RestaurantMemoRepo implements IRestaurantRepo {
    protected final Map<String, Restaurant> restaurantMap;

    public RestaurantMemoRepo() {
        restaurantMap = new HashMap<>();
    }
    @Override
    public Restaurant addRestaurant(String ownerId, String restaurantName) {
        Restaurant restaurant = findByName(restaurantName);
        if (restaurant != null){
            throw new RestaurantAlreadyExistException();
        }

        if (ownerId == null || ownerId.isBlank()){
            throw new NullPointerException();
        }

        restaurant = new Restaurant(ownerId, restaurantName, 0);
        System.out.println("Add restaurant in memory: " + restaurant.getId());
        restaurantMap.put(restaurant.getId(), restaurant);
        findAll().stream().forEach(r -> {
            System.out.println("restaurant id: " + r.getId());
        });
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
        restaurantMap.replace(id, restaurant);
        return restaurant;
    }

    @Override
    public Restaurant deleteRestaurant(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidInputException();
        }
        if (!restaurantMap.containsKey(id)){
            throw new InvalidParamsException();
        }
        return restaurantMap.remove(id);
    }

    @Override
    public Restaurant findById(String id) {
        if(id == null || id.isBlank()) {
            throw new InvalidInputException();
        }

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
                .orElse(null);
    }


    @Override
    public Restaurant findByOwnerId(String ownerId) {
        return restaurantMap.values().stream()
                .filter(restaurant -> restaurant.getOwnerId().equals(ownerId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Restaurant findByProduct(String productId) {
        return restaurantMap.values().stream()
                .filter(restaurant -> {
                    Product product = restaurant.getProducts().stream().filter(p -> {
                        return p.getId().equals(productId);
                    }).findFirst().orElse(null);

                    return product.getId().equals(productId);
                })
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Restaurant> findAll() {
        return restaurantMap.values();
    }

    @Override
    public Collection<Restaurant> findByRating(int rating) {
        return findAll().stream()
                .filter(restaurant -> restaurant.getTotalRating() >= rating)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String id) {
        return restaurantMap.containsKey(id);
    }

}
