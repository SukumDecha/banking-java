package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.exceptions.restaurant.RestaurantNotFoundException;
import me.sit.dev.repository.IOrderRepo;
import me.sit.dev.service.IOrderService;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class OrderService implements IOrderService {

    private final IOrderRepo orderRepo;

    public OrderService(IOrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public IOrderRepo getOrderRepo() {
        return orderRepo;
    }

    @Override
    public int showOrderPagination(Collection<Order> orders, int page, int size) {
        if(page < 1) {
            System.out.println("Invalid page number");
            return 0;
        }

        int totalOrders = orders.size();
        int totalPages = (int) Math.ceil((double) totalOrders / size);
        if(totalOrders == 0) {
            System.out.println("No orders found");
            return 0;
        }

        orders = orders.stream().sorted()
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());

        int count = page == 1 ? 1 : (page - 1) * size + 1;
        for (Order order : orders) {
            System.out.printf("--------- Order #%d  ---------%n", count);
            System.out.println("Order ID: " + order.getId());
            System.out.println("Order Status: " + order.getStatus());
            System.out.println("Order Date: " + new Date(order.getOrderAt()));
            if(order.getProducts().isEmpty()) {
                System.out.println("No products in this order");
                System.out.println("---------------------------");
                count++;
                continue;
            }
            System.out.println("Products:");
            for (Product product : order.getProducts().keySet()) {
                System.out.println("- " + product.getName() + " x" + order.getProducts().get(product));
            }
            System.out.println("Total Price: " + order.getTotalPrice());
            System.out.println("---------------------------");
            count++;
        }

        System.out.println("Current page: " + page + " / " + totalPages);
        return totalPages;
    }

    @Override
    public Order createOrder(User user, Restaurant restaurant) {
        Order order = orderRepo.createOrder(user, restaurant);
        // Send email to restaurant
        System.out.println("[S] --- Order confirmed ---");
        System.out.println("[S] Order ID: " + order.getId());
        System.out.println("[S] Restaurant Name: " + restaurant.getName() + " (" + restaurant.getId() + ")");
        System.out.println();
        System.out.println("[S] Products:");
        order.getProducts().forEach((product, quantity) -> {
            System.out.println("[S] - " + product.getName() + " (" + product.getPrice() + ") " + " x" + quantity);
        });
        System.out.println("[S] Total Price: " + order.getTotalPrice());
        System.out.println("[S] ----------------------");

        // Add order to user and restaurant
        order.getProducts().entrySet().forEach(entry -> {
            Product product = entry.getKey();
            product.setQuantity(product.getQuantity() - entry.getValue());
            restaurant.updateProduct(product);
        });

        restaurant.getOrders().add(order);
        user.getOrders().add(order);
        user.getCart().clearCart();
        return order;
    }


    @Override
    public Order updateOrder(String orderId, Order order) {
        return orderRepo.updateOrder(orderId, order);
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
    public void showOrderDetails(Order order, boolean showStatus) {
        System.out.println("[S] ------- " + (showStatus ? "Order" : "Product") +  " Details ------");
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
        System.out.println("[S] ----------------------------\n");
    }

}