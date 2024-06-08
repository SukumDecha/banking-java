package me.sit.dev.service;

import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.IOrderRepo;

import java.util.Collection;

public interface IOrderService extends IOrderRepo {
    void showOrderDetails(Order order, boolean showStatus);

    void cancelOrder(User user, Order order);

    void deliverOrder(User user, Order order);

}
