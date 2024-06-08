package me.sit.dev.repository;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;

import java.util.Collection;

public interface IOrderRepo {

    Order createOrder(User user, Restaurant restaurant);

    Order updateOrder(String orderId, Order order);

    Order findById(String id);

    Collection<Order> findByUserId(String userId);

    Collection<Order> findAll();
}
