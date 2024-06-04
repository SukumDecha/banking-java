package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Session;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.entity.impl.user.UserRole;
import me.sit.dev.repository.IUserRepository;
import me.sit.dev.service.IUserService;
import me.sit.dev.service.UtilityService;

import java.util.Collection;

public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User register(String name, String email, String password, boolean isAdmin) {
        User user = new User(UtilityService.generateId(), name, email, password,
                isAdmin ? UserRole.SYSTEM_ADMIN : UserRole.USER);
        login(email, password);
        return userRepository.save(user);
    }

    @Override
    public void login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) return;

        if (user.getPassword().equals(password)) {
            Session.createSession(user);
        }

    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
