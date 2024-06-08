package me.sit.dev.repository.impl.order;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.exceptions.InvalidInputException;
import me.sit.dev.repository.IOrderRepo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderMemoRepo implements IOrderRepo {

    protected final Map<String, Order> orderMap = new HashMap<>();

    @Override
    public Order createOrder(User user, Restaurant restaurant) {
        if (user == null || restaurant == null) {
            throw new InvalidInputException("User or restaurant is not valid");
        }

        Order order = new Order(user, user.getCart(), restaurant.getName(), restaurant.getId());
        orderMap.put(order.getId(), order);
        return order;
    }

    @Override
    public Order updateOrder(String orderId, Order order) {
        if (orderId == null || order == null) {
            throw new InvalidInputException("Order or orderId is not valid");
        }

        if (findById(orderId) == null) {
            throw new InvalidInputException("Order not found");
        }

        orderMap.put(orderId, order);
        return order;
    }

    @Override
    public Order findById(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidInputException("Id is not valid");
        }
        return findAll().stream().filter(o -> o.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Collection<Order> findByUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new InvalidInputException("userId is not valid");
        }
        return findAll().stream().filter(o -> o.getOwnerId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public Collection<Order> findAll() {
        return orderMap.values();
    }
}
