package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.service.IOrderService;

public class OrderService implements IOrderService {

    @Override
    public void showOrderDetails(Order order) {
        System.out.println("[S] --- Order Details ---");
        System.out.println("[S] Order ID: " + order.getId());
        System.out.println("[S] Restaurant Name: " + order.getRestaurant().getName() + " (" + order.getRestaurant().getId() + ")");
        System.out.println();
        System.out.println("[S] Total Price: " + order.getTotalPrice());
        System.out.println("[S] Order Status: " + order.getStatus());
        System.out.println("[S] ----------------------");
    }

    @Override

    public void confirmOrder(User user, Restaurant restaurant) {
        Order order = new Order(user, restaurant);
        order.confirmOrder();

        // TODO: Make waiting time
        // Send email to restaurant
        System.out.println("[S] --- Order confirmed ---");
        System.out.println("[S] Order ID: " + order.getId());
        System.out.println("[S] Restaurant Name: " + restaurant.getName() + " (" + restaurant.getId() + ")");
        System.out.println();
        System.out.println("[S] Total Price: " + order.getTotalPrice());
        System.out.println("[S] ----------------------");

        // Add order to user and restaurant
        user.getOrders().add(order);
        restaurant.getOrders().add(order);
    }

    @Override

    public void cancelOrder(User user, Order order) {
        order.cancelOrder();

        // Send messages to user
        System.out.println("[S] --- Order canceled ---");
        System.out.println("[S] Order ID: " + order.getId());
        System.out.println("[S] Restaurant Name: " + order.getRestaurant().getName() + " (" + order.getRestaurant().getId() + ")");
        System.out.println();
        System.out.println("[S] Total Price: " + order.getTotalPrice());
        System.out.println("[S] ----------------------");

        user.getOrders().remove(order);
        order.getRestaurant().getOrders().remove(order);
    }

    @Override

    public void deliverOrder(User user, Order order) {
        order.deliverOrder();

        // Send messages to user
        System.out.println("[S] --- Order delivered ---");
        System.out.println("[S] Order ID: " + order.getId());
        System.out.println("[S] Restaurant Name: " + order.getRestaurant().getName() + " (" + order.getRestaurant().getId() + ")");
        System.out.println();
        System.out.println("[S] Estimated Delivery Time: 30 minutes");
        System.out.println("[S] Total Price: " + order.getTotalPrice());
        System.out.println("[S] ----------------------");
        user.getOrders().remove(order);
    }


}
