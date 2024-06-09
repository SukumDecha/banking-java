package me.sit.dev.entity.impl;

import me.sit.dev.entity.BaseEntity;

public class Product extends BaseEntity {
    private String restaurantId;
    private String name;
    private double price;
    private int quantity;


    public Product(String id, String restaurantId, String name, double price, int quantity) {
        super("P-" + id);
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    public String getName() {
        return name;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + getId() + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
