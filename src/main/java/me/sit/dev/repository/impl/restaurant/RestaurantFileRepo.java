package me.sit.dev.repository.impl.restaurant;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.exceptions.InvalidInputException;
import me.sit.dev.exceptions.restaurant.RestaurantExistException;
import me.sit.dev.repository.IRestaurantRepo;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RestaurantFileRepo extends RestaurantMemoRepo implements IRestaurantRepo {
    private final Map<String, Restaurant> restaurantMap = new HashMap<>();
    private final String path = "src/main/resources/users/";

    @Override
    public Restaurant addRestaurant(String ownerId, String restaurantName) {
        super.addRestaurant(ownerId,restaurantName);
        Restaurant restaurant = findByName(restaurantName);
        File file = getFileFromRestaurant(restaurant);
        try (ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))){
            writer.writeObject(findByOwnerId(ownerId));
            writer.flush();
        }catch (Exception e){
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return findByOwnerId(ownerId);
    }

    public Restaurant save(Restaurant restaurant) {
        if (restaurant == null) {
            throw new InvalidInputException("User cannot be null");
        }
        restaurantMap.put(restaurant.getId(), restaurant);

        File file = getFileFromRestaurant(restaurant);
        try (ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            writer.writeObject(restaurant);
            writer.flush();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

        return restaurant;
    }

    @Override
    public Restaurant updateRestaurant(String id, Restaurant restaurant) {
        super.updateRestaurant(id,restaurant);
        save(restaurant);
        return restaurant;
    }

    @Override
    public Restaurant deleteRestaurant(String id) {
        super.deleteRestaurant(id);
        save(findByOwnerId(id));
        return null;
    }
    private File getFileFromRestaurant(Restaurant restaurant) {
        return new File(path + restaurant.getName() + "-" + restaurant.getId() + ".txt");
    }

}
