package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.IOrderRepo;
import me.sit.dev.service.IOrderService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService implements IOrderService {

    private final List<Order> orders = new ArrayList<>();
    private final IOrderRepo orderRepo;

    public OrderService(IOrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public void showOrderDetails(Order order, boolean showStatus) {
        System.out.println("[S] --- Order Details ---");
        System.out.println("[S] Order ID: " + order.getId());
        System.out.println("[S] Restaurant Name: " + order.getRestaurantName() + " (" + order.getRestaurantId() + ")");
        System.out.println();
        System.out.println("[S] Products:");
        order.getProducts().forEach((product, quantity) -> {
            System.out.println("[S] - " + product.getName() + " x" + quantity);
        });
        System.out.println("[S] Total Price: " + order.getTotalPrice());
        if (showStatus) {
            System.out.println("[S] Status: " + order.getStatus());
        }
        System.out.println("[S] ----------------------");
    }

    @Override
    public Order createOrder(User user, Restaurant restaurant) {
        Order order = new Order(user, user.getCart(), restaurant.getName(), restaurant.getId());
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
        restaurant.getOrders().add(order);
        user.getOrders().add(order);
        return order;
    }

    @Override
    public Order findById(String id) {
        return orderRepo.findById(id);
    }

    @Override
    public Collection<Order> findByUserId(String userId) {
        return orderRepo.findByUserId(userId);
    }

    @Override
    public Collection<Order> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public void cancelOrder(User user, Order order) {
        order.cancelOrder();

        // Send messages to user
        System.out.println("[S] --- Order canceled ---");
        System.out.println("[S] Order ID: " + order.getId());
        System.out.println("[S] Restaurant Name: " + order.getRestaurantName() + " (" + order.getRestaurantId() + ")");
        System.out.println();
        System.out.println("[S] Total Price: " + order.getTotalPrice());
        System.out.println("[S] ----------------------");
    }

    @Override
    public void deliverOrder(User user, Order order) {
        order.deliverOrder();

        // Send messages to user
        System.out.println("[S] --- Order delivered ---");
        System.out.println("[S] Order ID: " + order.getId());
        System.out.println("[S] Restaurant Name: " + order.getRestaurantName() + " (" + order.getRestaurantId() + ")");
        System.out.println();
        System.out.println("[S] Estimated Delivery Time: 30 minutes");
        System.out.println("[S] Total Price: " + order.getTotalPrice());
        System.out.println("[S] ----------------------");
    }


}
