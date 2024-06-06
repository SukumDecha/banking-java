package me.sit.dev.service;

import me.sit.dev.repository.IUserRepo;

public interface IUserService extends IUserRepo
{
    boolean register(String name, String email, String password, boolean isAdmin);

    boolean login(String email, String password);

}
