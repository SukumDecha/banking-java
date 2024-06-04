package me.sit.dev;

import me.sit.dev.entity.impl.user.User;
import me.sit.dev.entity.impl.user.UserRole;
import me.sit.dev.repository.IUserRepo;
import me.sit.dev.repository.impl.user.UserFileRepo;
import me.sit.dev.service.UtilityService;
import me.sit.dev.service.impl.UserService;

public class Application {

    public static void main(String[] args) {
//        User s = new User(34,"name","mail","password", USER);

        UserService service = new UserService(new UserFileRepo());
        service.register("name","mail","password",false);


    }
}
