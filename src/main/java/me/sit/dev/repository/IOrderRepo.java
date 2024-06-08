package me.sit.dev.repository;

import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;

import java.util.Collection;

public interface IOrderRepo {

    Order createOrder(User user);

    Order findById(String id);

    Collection<Order> findByUserId(String userId);

    Collection<Order> findAll();
}
