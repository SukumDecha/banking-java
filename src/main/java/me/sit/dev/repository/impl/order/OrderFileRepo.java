package me.sit.dev.repository.impl.order;

import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.exceptions.InvalidInputException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OrderFileRepo extends OrderMemoRepo {
    private final String path = "orders/";

    public OrderFileRepo() {
        super();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                try (ObjectInputStream reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)))) {
                    Order order = (Order) reader.readObject();
                    orderMap.put(order.getId(), order);
                } catch (Exception e) {
                    System.err.println("Error reading from file: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public Order createOrder(User user, Restaurant restaurant) {
        Order order = super.createOrder(user, restaurant);

        File file = getFileFromOrder(order);

        try (ObjectOutputStream write = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));){
            write.writeObject(order);
            write.flush();
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        return order;
    }

    @Override
    public Order updateOrder(String orderId, Order newOrder) {
        newOrder = super.updateOrder(orderId, newOrder);
        File file = getFileFromOrder(newOrder);

        try (ObjectOutputStream write = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));){
            write.writeObject(newOrder);
            write.flush();
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        return newOrder;
    }


    private File getFileFromOrder(Order order) {
        return new File(path + order.getOwnerId() + ".ser");
    }
}
