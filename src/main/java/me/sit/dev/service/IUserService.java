package me.sit.dev.service;

import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.IUserRepo;

public interface IUserService extends IUserRepo
{
    User register(String name, String email, String password, boolean isAdmin);

    void login(String email, String password);

    void delete(User user);
}
