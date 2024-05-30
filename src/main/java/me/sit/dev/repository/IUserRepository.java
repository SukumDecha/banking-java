package me.sit.dev.repository;

import me.sit.dev.entity.User;

import java.util.Collection;
public interface IUserRepository {

    boolean createUser(String username, String password);

    boolean deleteUser(String username);

    boolean updateUser(String username, String password);

    boolean userExists(String username);

    boolean authenticate(String username, String password);

    User getUser(String username);


    Collection<User> getAllUsers();





}
