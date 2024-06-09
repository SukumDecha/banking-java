package me.sit.dev.entity.impl;

import me.sit.dev.entity.BaseEntity;
import me.sit.dev.entity.impl.order.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Restaurant extends BaseEntity implements Comparable<Restaurant> {
    private final String name;
    private final String ownerId;
    private int totalRating;
    private final List<Product> products = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    public Restaurant(String ownerId, String restaurantName, int totalRating) {
        super("R-" + ownerId);
        this.ownerId = ownerId;
        this.name = restaurantName;
        this.totalRating = totalRating;
    }

    public Restaurant(String id, String ownerId, String restaurantName, int totalRating) {
        super(id);
        this.ownerId = ownerId;
        this.name = restaurantName;
        this.totalRating = totalRating;
    }

    public String getOwnerId(){
        return ownerId;
    }
    public String getName() {
        return name;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public double getRating() {
        if (orders.isEmpty()) {
            return 0.0;
        }
        return (double) totalRating / orders.size();
    }
    public List<Product> getProducts() {
        return products;
    }
    public List<Order> getOrders() {
        return orders;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }


    public void addProduct(Product product) {
        products.add(product);
    }

    public void updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(product.getId())) {
                products.set(i, product);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", totalRating=" + totalRating +
                ", products=" + products +
                ", orders=" + orders +
                '}';
    }

    @Override
    public int compareTo(Restaurant o) {
        return o.getRating() - this.getRating() > 0 ? 1 : -1;
    }
}