package me.sit.dev.ui.impl;

import me.sit.dev.entity.impl.Session;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.exceptions.InvalidPasswordException;
import me.sit.dev.exceptions.user.UserNotFoundException;
import me.sit.dev.service.ServiceFactory;
import me.sit.dev.ui.BaseUI;

import java.util.Scanner;

public class LoginUI extends BaseUI {

    private final ClientUI clientUI;
    private final RestaurantUI restaurantUI;
    private final String login_Prompt = """
            -------------- AUTH MENU --------------
                           1. Login
                          2. Register         
                      3. Exit from program
            ----------------------------------------       
            """;

    public LoginUI(ClientUI clientUI, RestaurantUI restaurantUI, ServiceFactory serviceFactory) {
        super("Login UI", "This UI only shows the login view.", serviceFactory);

        this.clientUI = clientUI;
        this.restaurantUI = restaurantUI;
    }

    @Override
    public void show() {
        System.out.println("--- Login UI ---");
        System.out.println(login_Prompt);
        System.out.println("please Enter 1/2/3");
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNext("[1|2|3]")) {
            System.out.println("please try again");
            sc.next();
        }
        int loginSelected = sc.nextInt();
        switch (loginSelected) {
            case 1: // login
                System.out.println("Login method");
                while (true) {
                    try {
                        System.out.print("Enter your email : ");
                        String email = sc.next();
                        System.out.print("Enter your password : ");
                        String password = sc.next();
                        if (userService.login(email, password)) {
                            System.out.println("Login successful");
                            sc.nextLine();
                            break;
                        }
                    } catch (InvalidPasswordException | UserNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                        System.out.println("You can enter 0 to end this process. Otherwise, press any key to try again.");
                        if (sc.next().equals("0")) {
                            System.out.println("Exiting from program");
                            System.exit(0);
                        }
                    }
                }
                break;
            case 2: // register
                System.out.println("Enter your name:");
                String name = sc.next();

                while (true) {
                    try {
                        System.out.println("Enter your email:");
                        String saveEmail = sc.next();
                        if (userService.existsByEmail(saveEmail)) {
                            System.out.println("Error: This email already exists");
                            continue;
                        }
                        System.out.println("Enter your password:");
                        String savePassword = sc.next();
                        System.out.println("Are you admin: (yes/no)");
                        while (!sc.hasNext("(?i)Yes|(?i)No")) {
                            sc.next();
                        }

                        String input = sc.next();
                        boolean isAdmin = false;
                        if (input.equals("yes")) {
                            isAdmin = true;
                        }
                        userService.register(name, saveEmail, savePassword, isAdmin);
                        break;
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                break;
            case 3:
                System.exit(0);
                break;
        }
        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you a client? (y/n)");
        String answer = scanner.nextLine();
        boolean isClient = answer.equals("y");

        if (isClient) {
            clientUI.show();
        } else {
            restaurantUI.show();
        }

    }
}
