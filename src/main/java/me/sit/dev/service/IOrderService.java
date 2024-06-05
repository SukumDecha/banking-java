package me.sit.dev.service;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;

public interface IOrderService {
    void showOrderDetails(Order order, boolean showStatus);

    void cancelOrder(User user, Order order);

    void deliverOrder(User user, Order order);

    Order createOrder(User user);
}
