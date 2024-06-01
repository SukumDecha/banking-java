package me.sit.dev.main;

import me.sit.dev.service.UserService;

public class Application {

    public static void main(String[] args) {
        UserService service = new UserService();

        service.createUser("nine","1234567fds","nine@kmutt.ac.th");
        service.createUser("bomb","fdffsfdsf","bomb@kmutt.ac.th");
        service.createUser("nay","gfsdmkghs","nay@kmutt.ac.th");
        service.createUser("may","t4otp3","may@kmutt.ac.th");

        System.out.println(service.getAllUsers());

        System.out.println(service.getUser("nay"));

        System.out.println(service.deleteUser("nay"));

        System.out.println(service.getUser("nay"));

    }
}
