package me.sit.dev.ui.impl;

import me.sit.dev.service.ServiceFactory;
import me.sit.dev.ui.BaseUI;

import java.util.Scanner;

public class LoginUI extends BaseUI {

    private final ClientUI clientUI;
    private final RestaurantUI restaurantUI;
    private final String login_Prompt = """
            -------------- LOGIN MENU --------------
                           1. Login
                          2. register         
                      3. out from program
            ----------------------------------------       
            """;

    public LoginUI(ClientUI clientUI, RestaurantUI restaurantUI, ServiceFactory serviceFactory) {
        super("Login UI", "This UI only shows the login view.", serviceFactory);

        this.clientUI = clientUI;
        this.restaurantUI = restaurantUI;
    }

    @Override
    public void show() {
        System.out.println("Login UI");
        System.out.println(login_Prompt);
        System.out.println("please Enter 1/2/3");
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNext("[1|2|3]")) {
            System.out.println("please try again");
            sc.next();
        }
        int loginSelected = sc.nextInt();
        boolean login_status = true;
        switch (loginSelected) {
            case 1: // login
                System.out.println("Login method");
                System.out.print("Enter your email : ");
                String email = sc.next();
                System.out.print("Enter your password : ");
                String password = sc.next();
                if (userService.login(email, password)) {
                    System.out.println("Success login");
                }
                break;
            case 2: // register
                System.out.println("Enter your Name");
                String name = sc.next();
                System.out.println("Enter your Email");
                String saveEmail = sc.next();
                System.out.println("Enter your Password");
                String savePassword = sc.next();
                System.out.println("Are you admin");
                while (!sc.hasNext("(?i)Yes|(?i)No")) {
                    System.out.println("please enter yes or no");
                    sc.next();
                }
                String input = sc.next();
                boolean isAdmin = false;
                if (input.equals("yes")) {
                    isAdmin = true;
                }
                userService.register(name, saveEmail, savePassword, isAdmin);
                break;
            case 3:
                break;
        }

        boolean isClient;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you a client? (y/n)");
        String answer = scanner.nextLine();
        isClient = answer.equals("y");

        if (isClient) {
            clientUI.show();
        } else {
            restaurantUI.show();
        }
    }
}
