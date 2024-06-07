package me.sit.dev.repository.impl.user;

import me.sit.dev.entity.impl.user.User;
import me.sit.dev.exceptions.InvalidInputException;
import me.sit.dev.repository.IUserRepo;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserFileRepo implements IUserRepo {
    private final Map<String,User> userMap = new HashMap<>();
    private final String path = "src/main/resources/";

//    public UserFileRepo(){
//        try (FileInputStream inputStream = new FileInputStream(new BufferedInputStream(new FileInputStream()))){
//
//        }catch (Exception e){
//
//        }
//    }
    @Override
    public Collection<User> findAll() {
        return userMap.values();
    }

    @Override
    public User findById(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidInputException();
        }
        return userMap.get(id);
    }

    @Override
    public User findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidInputException();
        }
        return userMap.values().stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public User save(User user) {
        String finalPath = path + user.getName() + "-" + user.getId() + ".txt";
        try (ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(finalPath)))) {
                writer.writeObject(user);
                writer.flush();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        return user;
    }

    @Override
    public User update(String userId, User user) {
        if (userId == null || userId.isBlank()){
            throw new NullPointerException("User ID is blank.");
        }
        if (!userMap.containsKey(userId)){
            return save(user);
        }
        return userMap.put(userId,user);
    }

    @Override
    public void delete(User user) {
        if (user == null) throw new NullPointerException("User is null.");
        userMap.remove(user.getId(),user);
    }

    @Override
    public void deleteById(String id) {
        if (id == null || id.isBlank()){
            throw new InvalidInputException();
        }
        userMap.remove(id);
    }

    @Override
    public void deleteAll() {
        userMap.clear();
    }

    @Override
    public boolean existsById(String id) {
        if (id == null || id.isBlank()){
            throw new InvalidInputException();
        }
        return userMap.containsKey(id);
    }


    @Override
    public boolean existsByEmail(String email) {
        return userMap.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }
}
