package me.sit.dev.repository;

import me.sit.dev.entity.impl.user.User;

import java.util.Collection;
public interface IUserRepo {

    Collection<User> findAll();

    User findById(String id);

    User findByEmail(String email);

    User save(User user);

    User update(User user);

    void delete(User user);

    void deleteById(String id);

    void deleteAll();

    boolean existsById(String id);

    boolean existsByEmail(String email);

}
