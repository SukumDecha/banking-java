package me.sit.dev.repository.impl.order;

import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;

import java.util.Collection;

public class OrderDatabaseRepo extends OrderMemoRepo {
    @Override
    public Order createOrder(User user) {
        return super.createOrder(user);
    }

    @Override
    public Collection<Order> findAll() {
        return super.findAll();
    }
}
