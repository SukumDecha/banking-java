package me.sit.dev.service;

import me.sit.dev.entity.User;
import me.sit.dev.repository.IUserRepository;

import java.util.Collection;

public class UserService implements IUserRepository {
    @Override
    public boolean createUser(String username, String password) {
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }

    @Override
    public boolean updateUser(String username, String password) {
        return false;
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public Collection<User> getAllUsers() {
        return null;
    }
}
