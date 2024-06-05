package me.sit.dev.entity.impl.order;

import me.sit.dev.entity.BaseEntity;
import me.sit.dev.entity.impl.Cart;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.service.standalone.UtilityService;

public class Order extends BaseEntity {

    private final String ownerId;
    private final Cart cart;
    private final Restaurant restaurant;
    private OrderStatus status;

    private final long orderAt = System.currentTimeMillis();

    public Order(User user, Restaurant restaurant) {
        super(UtilityService.generateId("O-"));
        this.restaurant = restaurant;
        this.ownerId = user.getId();
        this.cart = user.getCart();
        this.status = OrderStatus.CONFIRMED;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public Cart getCart() {
        return cart;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public long getOrderAt() {
        return orderAt;
    }

    public void confirmOrder() {
        cart.clearCart();

        status = OrderStatus.CONFIRMED;
    }

    public void deliverOrder() {
        cart.clearCart();

        status = OrderStatus.DELIVERED;
    }

    public void cancelOrder() {
        cart.clearCart();

        status = OrderStatus.CANCELLED;
    }

    public double getTotalPrice() {
        return cart.getProducts().entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}
