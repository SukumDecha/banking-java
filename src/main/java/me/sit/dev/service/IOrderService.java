package me.sit.dev.service;

import me.sit.dev.entity.impl.order.Order;

public interface IOrderService {
    void showOrderDetails(Order order, boolean showStatus);

    void cancelOrder(User user, Order order);

    void deliverOrder(User user, Order order);

    Order createOrder(User user);
}
