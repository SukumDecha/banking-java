package me.sit.dev.repository.impl.order;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;

public class OrderFileRepo extends OrderMemoRepo {

    public OrderFileRepo() {

    }


    @Override
    public Order createOrder(User user, Restaurant restaurant) {
        Order order = super.createOrder(user, restaurant);

        return order;
    }

    @Override
    public Order updateOrder(String orderId, Order order) {
        order = super.updateOrder(orderId, order);

        return order;
    }

}
