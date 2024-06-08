package me.sit.dev.repository.impl.order;

import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.IOrderRepo;

import java.util.Collection;

public class OrderMemoRepo implements IOrderRepo {


    @Override
    public Order createOrder(User user) {
        return null;
    }

    @Override
    public Order findById(String id) {
        return null;
    }

    @Override
    public Collection<Order> findByUserId(String userId) {
        return null;
    }

    @Override
    public Collection<Order> findAll() {
        return null;
    }
}
