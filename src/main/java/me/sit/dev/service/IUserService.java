package me.sit.dev.service;

import me.sit.dev.entity.impl.user.User;

import java.util.Collection;

public interface IUserService {
    Collection<User> findAll();

    User findById(Long id);

    User register(String name, String email, String password, boolean isAdmin);

    void login(String email, String password);

    void save(User user);

    void delete(User user);

    void deleteById(Long id);

    void deleteAll();

    boolean existsById(Long id);

    User findByEmail(String email);

}
