package me.sit.dev.entity.impl.order;

import me.sit.dev.entity.BaseEntity;
import me.sit.dev.entity.impl.Cart;
import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.user.User;

import java.util.HashMap;
import java.util.Map;

public class Order extends BaseEntity {
    private final String ownerId, restaurantId, restaurantName;

    private Map<Product, Integer> products;
    private OrderStatus status;
    private final long orderAt = System.currentTimeMillis();

    public Order(User user, Cart cart, String restaurantId, String restaurantName) {
        super("order-" + user.getId() + "-" + user.getOrders().size());
        this.ownerId = user.getId();
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.products = cart.getProducts();

        confirmOrder();
    }

    public Order(String id, String ownerId, String restaurantId, String restaurantName, String productMap, String status) {
        super(id);
        this.ownerId = ownerId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.status = OrderStatus.valueOf(status);

    }

    public String getOwnerId() {
        return ownerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public long getOrderAt() {
        return orderAt;
    }

    public void confirmOrder() {

        status = OrderStatus.CONFIRMED;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void deliverOrder() {
        status = OrderStatus.DELIVERED;
    }

    public void cancelOrder() {
        status = OrderStatus.CANCELLED;
    }

    public double getTotalPrice() {
        return products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }
}
