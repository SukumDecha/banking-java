package me.sit.dev.constants;

import me.sit.dev.entity.impl.user.UserRole;

public class SystemAdmin {
    private static User SYSTEM_ADMIN;

    public static User getSystemAdmin() {
        return SYSTEM_ADMIN == null ?
                SYSTEM_ADMIN = new User("system-admin", "System Admin",
                        "admin123@gmail.com"
                        , "admin123", UserRole.SYSTEM_ADMIN) : SYSTEM_ADMIN;
    }


}
