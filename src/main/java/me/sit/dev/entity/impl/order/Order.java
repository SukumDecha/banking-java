package me.sit.dev.entity.impl.order;

import me.sit.dev.entity.BaseEntity;
import me.sit.dev.entity.impl.Cart;
import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.user.User;

import java.util.HashMap;
import java.util.Map;

public class Order extends BaseEntity implements Comparable<Order> {
    private final String ownerId, restaurantId, restaurantName;

    private Map<Product, Integer> products;
    private OrderStatus status;
    private final long orderAt = System.currentTimeMillis();

    public Order(User user, Cart cart, String restaurantId, String restaurantName) {
        super("order-" + user.getId() + "-" + user.getOrders().size());
        this.ownerId = user.getId();
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;

        Map<Product, Integer> productMap = new HashMap<>();
        cart.getProducts().forEach(productMap::put);

        this.products = productMap;
    }

    public Order(String id, String ownerId, String restaurantId, String restaurantName, Map<Product, Integer> productMap, String status) {
        super(id);
        this.ownerId = ownerId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.status = OrderStatus.valueOf(status);

        this.products = productMap;
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

    @Override
    public int compareTo(Order o) {
        return o.getOrderAt() - orderAt > 0 ? 1 : -1;
    }
}
