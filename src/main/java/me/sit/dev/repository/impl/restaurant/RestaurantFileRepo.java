package me.sit.dev.repository.impl.restaurant;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.repository.IRestaurantRepo;

import java.io.*;

public class RestaurantFileRepo extends RestaurantMemoRepo implements IRestaurantRepo {
    private final String path = "src/main/resources/restaurants/";

    public RestaurantFileRepo() {
        super();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                try (ObjectInputStream reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)))) {
                    Restaurant restaurant = (Restaurant) reader.readObject();
                    restaurantMap.put(restaurant.getId(), restaurant);
                } catch (Exception e) {
                    System.err.println("Error reading from file: " + e.getMessage());
                }
            }
        }
    }
    @Override
    public Restaurant addRestaurant(String ownerId, String restaurantName) {
        Restaurant restaurant = super.addRestaurant(ownerId, restaurantName);

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
        Restaurant updatedRestaurant = super.updateRestaurant(id, restaurant);

        File file = getFileFromRestaurant(updatedRestaurant);
        try (ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            writer.writeObject(updatedRestaurant);
            writer.flush();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

        return updatedRestaurant;
    }

    @Override
    public Restaurant deleteRestaurant(String id) {
        Restaurant restaurant = super.deleteRestaurant(id);

        File file = getFileFromRestaurant(restaurant);
        if(!file.delete()) {
            System.err.println("Error deleting file: " + file.getName());
        }

        return restaurant;
    }

    private File getFileFromRestaurant(Restaurant restaurant) {
        return new File(path + restaurant.getId() + ".ser");
    }
}
