package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.Session;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.entity.impl.user.UserRole;
import me.sit.dev.exceptions.InvalidParamsException;
import me.sit.dev.exceptions.InvalidPasswordException;
import me.sit.dev.exceptions.user.UserNotFoundException;
import me.sit.dev.repository.IUserRepo;
import me.sit.dev.service.IUserService;

import java.util.Collection;

public class UserService implements IUserService {
    private final IUserRepo userRepository;

    public UserService(IUserRepo userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all users.
     *
     * @return a collection of all users
     */
    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the ID of the user to find
     * @return null if the user was not found (this is a placeholder implementation)
     */
    @Override
    public User findById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user to find
     * @return null if the user was not found
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Saves a new user to the repository.
     *
     * @param user the user to save
     * @return null if the user was not successfully saved
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Updates an existing user with the given ID using the provided user details.
     *
     * @param userId the ID of the user to update
     * @param user   the updated user object
     * @return null if the user was not successfully updated
     */
    @Override
    public User update(String userId, User user) {
        return userRepository.update(userId, user);
    }

    /**
     * Deletes a user.
     *
     * @param user the user to delete
     */
    @Override
    public void delete(User user) {
        // Implementation needed for deleting the user
        userRepository.delete(user);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    /**
     * Deletes all users.
     */
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    /**
     * Checks if a user with the given ID exists.
     *
     * @param id the ID of the user to check
     * @return false if the user does not exist
     */
    @Override
    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }

    /**
     * Checks if a user with the given email exists.
     *
     * @param email the email of the user to check
     * @return false if the user does not exist
     */
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Registers a new user with the given name, email, password, and admin status.
     *
     * @param name     the name of the user
     * @param email    the email of the user
     * @param password the password of the user
     * @param isAdmin  the admin status of the user
     * @return true if the user was successfully registered
     * @throws InvalidParamsException if any of the parameters are invalid
     */
    @Override
    public boolean register(String name, String email, String password, boolean isAdmin) {
        if (name == null || name.isBlank()) {
            throw new InvalidParamsException("Username cannot be blank");
        }
        if (email == null || email.isBlank()) {
            throw new InvalidParamsException("Email cannot be blank");
        }

        if(!email.endsWith("@gmail.com")) {
            throw new InvalidParamsException("Email must be a Gmail account");
        }

        if (existsByEmail(email)) {
            throw new InvalidParamsException("This email has already been registered");
        }
        if (password == null || password.isBlank()) {
            throw new InvalidParamsException("Password cannot be blank");
        }

        User user = new User("U" + findAll().size(), name, email, password, isAdmin ? UserRole.SYSTEM_ADMIN : UserRole.USER);
        userRepository.save(user);
        System.out.println("User registered successfully");
        return true;
    }

    /**
     * Logs in a user with the given email and password.
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @return false if the user was not successfully logged in
     */
    @Override
    public boolean login(String email, String password) {
        User user = findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }

        Session.createSession(user);
        return true;
    }

    /**
     * Logs out the current user.
     *
     * @return false if the user was not successfully logged out
     */
    @Override
    public boolean logout() {
        Session.destroySession();
        return true;
    }
}