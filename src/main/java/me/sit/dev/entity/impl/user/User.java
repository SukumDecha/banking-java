package me.sit.dev.entity.impl.user;

import me.sit.dev.entity.BaseEntity;
import me.sit.dev.entity.impl.Cart;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.OrderStatus;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseEntity {
    private final String name, email, password;
    private final UserRole role;
    private Cart cart;
    private Restaurant restaurant;
    private final List<Order> orders = new ArrayList<>();

    public User(String id, String name, String email, String password, UserRole role) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.cart = new Cart();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public Cart getCart() {
        return cart;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
    public List<Order> getOrders() {
        return orders;
    }

    public List<Order> getOrderHistory() {
        return orders.stream().filter(order -> order.getStatus() == OrderStatus.DELIVERED
                || order.getStatus() == OrderStatus.CANCELLED).toList();
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
