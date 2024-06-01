package me.sit.dev.repository;

import me.sit.dev.entity.User;
import me.sit.dev.service.UserService;

import java.util.Collection;
public interface IUserRepository {

    boolean createUser(String username, String password, String email);

    boolean deleteUser(String username);

    boolean userExists(String username);

    boolean authenticate(String username, String password);

    User getUser(String username);


    Collection<User> getAllUsers();





}
