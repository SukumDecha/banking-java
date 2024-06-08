package me.sit.dev.repository.impl.order;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.IOrderRepo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderMemoRepo implements IOrderRepo {

    protected final Map<String, Order> orderMap = new HashMap<>();

    @Override
    public Order createOrder(User user, Restaurant restaurant) {
        return null;
    }

    @Override
    public Order updateOrder(String orderId, Order order) {
        return null;
    }

    @Override
    public Order findById(String id) {
        return findAll().stream().filter(o -> o.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Collection<Order> findByUserId(String userId) {
        return findAll().stream().filter(o -> o.getOwnerId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public Collection<Order> findAll() {
        return orderMap.values();
    }
}
