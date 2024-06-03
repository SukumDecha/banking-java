package me.sit.dev.repository;

import me.sit.dev.entity.impl.user.User;

import java.util.Collection;
public interface IUserRepository {

    Collection<User> findAll();

    User findById(Long id);

    User findByEmail(String email);

    User save(User user);

    User update(User user);

    void delete(User user);

    void deleteById(Long id);

    void deleteAll();

    boolean existsById(Long id);

    boolean existsByEmail(String email);

}
