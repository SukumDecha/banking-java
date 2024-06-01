package me.sit.dev.service;

import me.sit.dev.Exception.CreateUserException;
import me.sit.dev.entity.User;
import me.sit.dev.repository.IUserRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static me.sit.dev.entity.enums.Role.USER;

public class UserService implements IUserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public boolean createUser(String username, String password, String email) {
        if (username == null || username.isBlank()) {
            throw new CreateUserException("Username cannot be blank");
        }

        if (password == null || password.isBlank()){
            throw new CreateUserException("Password cannot be blank");
        }

        if (email == null || email.isBlank()){
            throw new CreateUserException("Email cannot be blank");
        }

        User newUser = new User(username,password,email,USER);
        users.put(username,newUser);
        return true;
    }

    @Override
    public boolean deleteUser(String username) {
        if (users.containsKey(username)){
        users.remove(username);
        return true;
        }else {
            throw new NullPointerException("Username not match");
        }
    }

    @Override
    public boolean userExists(String username) {
        for (User value : users.values()) {
            if (value.getName().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }

    @Override
    public User getUser(String username) {
        for (User value : users.values()) {
            if (value.getName().equalsIgnoreCase(username)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }
}
