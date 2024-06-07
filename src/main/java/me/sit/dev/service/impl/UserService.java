package me.sit.dev.service.impl;

import me.sit.dev.entity.impl.user.User;
import me.sit.dev.entity.impl.user.UserRole;
import me.sit.dev.exceptions.InvalidParamsException;
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
        return null;
    }

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user to find
     * @return null if the user was not found 
     */
    @Override
    public User findByEmail(String email) {
        return null;
    }

    /**
     * Saves a new user to the repository.
     *
     * @param user the user to save
     * @return null if the user was not successfully saved 
     */
    @Override
    public User save(User user) {
        return null;
    }

    /**
     * Updates an existing user with the given ID using the provided user details.
     *
     * @param userId the ID of the user to update
     * @param user the updated user object
     * @return null if the user was not successfully updated 
     */
    @Override
    public User update(String userId, User user) {
        return null;
    }

    /**
     * Deletes a user.
     *
     * @param user the user to delete
     */
    @Override
    public void delete(User user) {
        // Implementation needed for deleting the user
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    @Override
    public void deleteById(String id) {
        // Implementation needed for deleting the user by ID
    }

    /**
     * Deletes all users.
     */
    @Override
    public void deleteAll() {
        // Implementation needed for deleting all users
    }

    /**
     * Checks if a user with the given ID exists.
     *
     * @param id the ID of the user to check
     * @return false if the user does not exist 
     */
    @Override
    public boolean existsById(String id) {
        return false;
    }

    /**
     * Checks if a user with the given email exists.
     *
     * @param email the email of the user to check
     * @return false if the user does not exist 
     */
    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    /**
     * Registers a new user with the given name, email, password, and admin status.
     *
     * @param name the name of the user
     * @param email the email of the user
     * @param password the password of the user
     * @param isAdmin the admin status of the user
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
        if (password == null || password.isBlank()) {
            throw new InvalidParamsException("Password cannot be blank");
        }

        User user = new User("U" + findAll().size(), name, email, password, UserRole.USER);
        userRepository.save(user);
        return true;
    }

    /**
     * Logs in a user with the given email and password.
     *
     * @param email the email of the user
     * @param password the password of the user
     * @return false if the user was not successfully logged in 
     */
    @Override
    public boolean login(String email, String password) {
        // Set session (implementation needed)
        return false;
    }
}
