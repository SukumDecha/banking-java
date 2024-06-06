package me.sit.dev.repository.impl.user;


import me.sit.dev.entity.impl.user.User;
import me.sit.dev.exceptions.user.UserExistException;
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
        if (id == null || id.isBlank()){
            throw new UserNotFoundException();
        }
        return userMap.get(id);
    }

    @Override
    public User findByEmail(String email) {
        if (email == null | email.isBlank()){
            throw new UserNotFoundException();
        }
        return userMap.get(email);
    }

    @Override
    public User save(User user) {
        if (userMap.containsKey(user.getId())) {
            throw new UserExistException();
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(String userId, User user) {
        if (!userMap.containsKey(user.getId())) {
            throw new UserNotFoundException();
        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public void delete(User user) {
        if (!userMap.containsKey(user.getId())) {
        throw new UserNotFoundException();
    }
        userMap.remove(user.getID());
}

    @Override
    public void deleteById(String id) {
        if (!userMap.containsKey(user.getId())) {
            throw new UserNotFoundException();
        }
        userMap.remove(id);


    @Override
    public void deleteAll() {
        userMap.clear();
        }
    }

    @Override
    public boolean existsById(String id) {
        return userMap.containsKey(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

}
