package me.sit.dev.entity.impl;

import me.sit.dev.entity.BaseEntity;
import me.sit.dev.service.UtilityService;

public class Product extends BaseEntity {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        super(UtilityService.generateId("P-"));
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
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
}
