package me.sit.dev.repository.impl.user;


import me.sit.dev.entity.impl.user.User;
import me.sit.dev.exceptions.InvalidInputException;
import me.sit.dev.exceptions.user.UserNotFoundException;
import me.sit.dev.repository.IUserRepo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserMemoRepo implements IUserRepo {
    private final Map<String, User> userMap = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        return userMap.values();
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
        if (email == null | email.isBlank()) {
            throw new InvalidInputException();
        }
        return userMap.get(email);
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new InvalidInputException();
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(String userId, User user) {
        if (user == null || userId == null || userId.isBlank()) {
            throw new InvalidInputException();
        }
        if (!userMap.containsKey(user.getId())) {
            throw new UserNotFoundException();
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(User user) {
        if (user == null) {
            throw new InvalidInputException();
        }
        if (!userMap.containsKey(user.getId())) {
            throw new UserNotFoundException();
        }
        userMap.remove(user.getId());
    }

    @Override
    public void deleteById(String id) {
        if (id == null) {
            throw new InvalidInputException();
        }
        if (!userMap.containsKey(id)) {
            throw new UserNotFoundException();
        }
        userMap.remove(id);
    }


    @Override
    public void deleteAll() {
        userMap.clear();
    }


    @Override
    public boolean existsById(String id) {
        return findById(id) != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

}
