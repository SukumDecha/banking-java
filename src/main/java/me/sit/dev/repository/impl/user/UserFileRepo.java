package me.sit.dev.repository.impl.user;

import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.IUserRepo;

import java.util.Collection;

public class UserFileRepo implements IUserRepo {
    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user) {
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
