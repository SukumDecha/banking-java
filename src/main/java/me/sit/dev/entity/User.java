package me.sit.dev.entity;

import me.sit.dev.entity.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name, email, password;
    private final Role role;;
    private List<Order> currentOrders = new ArrayList<>();
    private List<Order> orderHistory = new ArrayList<>();

    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public List<Order> getCurrentOrders() {
        return currentOrders;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", role=").append(role);
        sb.append(", currentOrders=").append(currentOrders);
        sb.append(", orderHistory=").append(orderHistory);
        sb.append("}\n");
        return sb.toString();
    }
}
