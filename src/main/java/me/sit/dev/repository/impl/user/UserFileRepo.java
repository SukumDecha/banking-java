package me.sit.dev.repository.impl.user;

import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.IUserRepo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserFileRepo extends UserMemoRepo implements IUserRepo {
    private final Map<String, User> userMap = new HashMap<>();
    private final String path = "src/main/resources/users/";

    public UserFileRepo() {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                try (ObjectInputStream reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)))) {
                    User user = (User) reader.readObject();
                    userMap.put(user.getId(), user);
                } catch (Exception e) {
                    System.err.println("Error reading from file: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public User save(User user) {
        super.save(user);

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
        super.update(userId, user);

        save(user);
        return user;
    }


    @Override
    public void delete(User user) {
        super.delete(user);
        String finalPath = path + user.getName() + "-" + user.getId() + ".txt";
        File file = new File(finalPath);

        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public void deleteById(String id) {
        User user = findById(id);

        delete(user);
    }

}