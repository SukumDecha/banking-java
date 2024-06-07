package me.sit.dev.entity.impl;

import me.sit.dev.entity.BaseEntity;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.service.standalone.UtilityService;

import java.util.ArrayList;
import java.util.List;

public class Restaurant extends BaseEntity {
    private final String name;
    private final String ownerId;
    private int totalRating;
    private final List<Product> products = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    public Restaurant(String ownerId, String restaurantName, int totalRating) {
        super(UtilityService.generateId("R-"));
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



}