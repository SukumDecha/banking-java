package me.sit.dev.repository.impl.user;


import me.sit.dev.entity.impl.user.User;
import me.sit.dev.exceptions.InvalidInputException;
import me.sit.dev.exceptions.user.UserNotFoundException;
import me.sit.dev.repository.IUserRepo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserMemoRepo implements IUserRepo {
    protected final Map<String, User> userMap = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        return userMap.values();
    }

    @Override
    public User findById(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidInputException("Id cannot be null or blank");
        }
        return userMap.get(id);
    }

    @Override
    public User findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidInputException("Email cannot be blank");
        }

        return userMap.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new InvalidInputException("User cannot be null");
        }

        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(String userId, User user) {
        if (user == null) {
            throw new InvalidInputException("User cannot be null");
        }

        if(userId == null || userId.isBlank()) {
            throw new InvalidInputException("Id cannot be null or blank");
        }

        if(findById(user.getId()) == null) {
            throw new UserNotFoundException();
        }

        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(User user) {
        if (user == null) {
            throw new InvalidInputException("User cannot be null");
        }

        if(findById(user.getId()) == null) {
            throw new UserNotFoundException();
        }

        userMap.remove(user.getId());
    }

    @Override
    public void deleteById(String id) {
        if (id == null) {
            throw new InvalidInputException("Id cannot be null or blank");
        }

        if(findById(id) == null) {
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
