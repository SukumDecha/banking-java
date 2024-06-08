package me.sit.dev.repository.impl.user;

import me.sit.dev.entity.impl.user.User;
import me.sit.dev.repository.IUserRepo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserFileRepo extends UserMemoRepo implements IUserRepo {
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

        File file = getFileFromUser(user);

        try (ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
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

        return save(user);
    }


    @Override
    public void delete(User user) {
        super.delete(user);

        File file = getFileFromUser(user);

        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);

        User user = findById(id);

        File file = getFileFromUser(user);

        if (file.exists()) {
            file.delete();
        }
    }

    private File getFileFromUser(User user) {
        return new File(path + user.getName() + "-" + user.getId() + ".txt");
    }

}