package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Session;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.entity.impl.user.UserRole;
import me.sit.dev.exceptions.InvalidParamsException;
import me.sit.dev.exceptions.InvalidPasswordException;
import me.sit.dev.exceptions.user.UserExistException;
import me.sit.dev.exceptions.user.UserNotFoundException;
import me.sit.dev.repository.IUserRepo;
import me.sit.dev.repository.impl.user.UserFileRepo;
import me.sit.dev.repository.impl.user.UserMemoRepo;
import me.sit.dev.repository.impl.user.UserSQLRepo;
import me.sit.dev.service.IUserService;
import me.sit.dev.service.UtilityService;

import java.util.Collection;

public class UserService implements IUserService {
    private final IUserRepo userRepository;

    public UserService(IUserRepo userRepository) {
        this.userRepository = userRepository;
    }


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

    @Override
    public User register(String name, String email, String password, boolean isAdmin) {
        if (name == null || name.isBlank()){
            throw  new InvalidParamsException("Username cannot be blank");
        }
        if (email == null || email.isBlank()){
            throw  new InvalidParamsException("email cannot be blank");
        }
        if (password == null || password.isBlank()){
            throw  new InvalidParamsException("password cannot be blank");
        }
        User user = new User("id",name,email,password,UserRole.USER);
        return userRepository.save(user);
    }

    @Override
    public void login(String email, String password) {

    }

    @Override
    public void delete(User user) {

    }
}
