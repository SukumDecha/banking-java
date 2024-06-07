package me.sit.dev.ui.impl;

import me.sit.dev.entity.impl.user.User;
import me.sit.dev.entity.impl.user.UserRole;
import me.sit.dev.service.standalone.ServiceFactory;
import me.sit.dev.ui.BaseUI;

public class ClientUI extends BaseUI {

    public ClientUI(ServiceFactory serviceFactory) {
        super("Client UI", "This UI only shows the client's view.", serviceFactory);

    }

    @Override
    public void show() {
        System.out.println("Client UI");
        User u = new User("21","nine","mail","pass", UserRole.USER);
        userService.register("nine","mail","pass",false);
        userService.register("65","wer","wetewt",false);
        System.out.println(u.getName());
        System.out.println(userService.register("nine","mail","pass",false));
        System.out.println(userService.findAll());
        System.out.println(userService.existsByEmail("mail"));


    }
}
