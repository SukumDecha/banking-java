package me.sit.dev.service.standalone;

import java.util.UUID;

public class UtilityService {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static String generateId(String prefix) {
        return prefix + UUID.randomUUID().toString();
    }
}
