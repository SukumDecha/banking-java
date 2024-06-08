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
    private final String service_Prompt = """
            -------------- MENU --------------
                        1. Client
                        2. Restaurant       
                        3. Logout 
            -----------------------------------      
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
                        if (input.equalsIgnoreCase("yes")) {
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

        semiMenu();
    }

    private void semiMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println(service_Prompt);
        System.out.print("Enter your choice: ");
        while (!sc.hasNext("(?i)[R].*|(?i)[C].*|(?i)[O].*|1|2|3")) {
            System.out.println("Please try again");
            sc.nextLine();
        }

        String answer = sc.next();
        if (answer.equalsIgnoreCase("1") || answer.contains("C")) {
            clientUI.show();
        } else if (answer.equalsIgnoreCase("2") || answer.contains("R")) {
            User currentUser = Session.getCurrentSession().getUser();
            if(currentUser.getRestaurant() == null) {
                System.out.println("You are not a restaurant owner");
                System.out.println("Going back to main menu");
                System.out.println("Otherwise, create one to view this menu.");
                semiMenu();
            } else {
                restaurantUI.show();
            }
        } else {
            userService.logout();
            System.out.println("Logout successful");
            show();
        }
    }
}
