package me.sit.dev.service;

import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.IUserRepository;

import java.util.Collection;

public interface IUserService extends IUserRepository
{
    User register(String name, String email, String password, boolean isAdmin);

    void login(String email, String password);

    void delete(User user);
}
