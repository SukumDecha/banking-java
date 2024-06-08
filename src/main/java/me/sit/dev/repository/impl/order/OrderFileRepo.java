package me.sit.dev.repository.impl.order;

import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;

public class OrderFileRepo extends OrderMemoRepo {
    private final String path = "src/main/resources/orders/";


    @Override
    public Order createOrder(User user) {
        return super.createOrder(user);
    }
}
