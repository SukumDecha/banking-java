package me.sit.dev.repository.impl.user;

import me.sit.dev.entity.impl.user.User;
import me.sit.dev.exceptions.InvalidInputException;
import me.sit.dev.repository.IUserRepo;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserFileRepo implements IUserRepo {
    private final Map<String, User> userMap = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public User findById(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidInputException();
        }
        return userMap.get(id);
    }

    @Override
    public User findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidInputException();
        }
        return userMap.values().stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    //
    @Override
    public User save(User user) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("src/main/resources/test.txt")))) {
                writer.writeObject(userMap);
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        return user;
    }

    @Override
    public User update(String userId, User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }
}
