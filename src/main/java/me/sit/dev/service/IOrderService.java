package me.sit.dev.service;

import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.IOrderRepo;

import java.util.Collection;

public interface IOrderService extends IOrderRepo {
    void showOrderDetails(Order order, boolean showStatus);
    IOrderRepo getOrderRepo();


}
