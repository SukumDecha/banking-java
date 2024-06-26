package me.sit.dev.ui.impl;

import me.sit.dev.entity.impl.Session;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.entity.impl.user.UserRole;
import me.sit.dev.exceptions.InvalidPasswordException;
import me.sit.dev.exceptions.user.UserNotFoundException;
import me.sit.dev.service.ServiceFactory;
import me.sit.dev.ui.BaseUI;

import java.io.Console;
import java.util.Scanner;

public class LoginUI extends BaseUI {

    private final ClientUI clientUI;
    private final RestaurantUI restaurantUI;
    private final AdminUI adminUI;

    private final Console console = System.console();

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
            ----------------------------------      
            """;

    public LoginUI(ClientUI clientUI, RestaurantUI restaurantUI, ServiceFactory serviceFactory, AdminUI adminUI) {
        super("Login UI", "This UI only shows the login view.", serviceFactory);

        this.clientUI = clientUI;
        this.restaurantUI = restaurantUI;
        this.adminUI = adminUI;
    }

    @Override
    public void show() {
        System.out.println(login_Prompt);
        System.out.print("[!] Please enter 1|2|3 : ");
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNext("[1|2|3]")) {
            System.out.print("[!] Please try again. (Enter 1|2|3) : ");
            sc.next();
        }
        int loginSelected = sc.nextInt();
        int checkIsAdmin = 0;
        switch (loginSelected) {
            case 1: // login
                System.out.println("[S] Logging in...");
                while (true) {
                    try {
                        System.out.print("Enter your email : ");
                        String email = sc.next();
                        if(console != null) {
                            char[] passwordArray = console.readPassword("Enter your password : ");
                            String password = new String(passwordArray);
                            if (userService.login(email, password)) {
                                System.out.println("----------------------------------");
                                System.out.println("\t Login successful");
                                System.out.println("----------------------------------");
                                sc.nextLine();
                            }
                            if (userService.findByEmail(email).getRole() == UserRole.SYSTEM_ADMIN) {
                                checkIsAdmin++;
                                adminUI.show();
                            }
                        } else {
                            System.out.print("Enter your password : ");
                            String password = sc.next();
                            if (userService.login(email, password)) {
                                System.out.println("----------------------------------");
                                System.out.println("\tLogin successful");
                                System.out.println("----------------------------------");
                                sc.nextLine();
                            }

                            if (userService.findByEmail(email).getRole() == UserRole.SYSTEM_ADMIN) {
                                checkIsAdmin++;
                                adminUI.show();
                            }
                        }
                        break;
                    } catch (InvalidPasswordException | UserNotFoundException e) {
                        System.out.println("[!] Error: " + e.getMessage());
                        System.out.println("\n[!] You can enter 0 to end this process.\n[!] Otherwise, press any key to try again.");
                        if (sc.next().equals("0")) {
                            System.out.println("Exiting from program");
                            show();
                            break;
                        }
                    }
                }
                break;
            case 2: // register
                System.out.print("Enter your name : ");
                String name = sc.next();
                while (true) {
                    try {
                        System.out.print("Enter your email : ");
                        String saveEmail = sc.next();
                        if (saveEmail.equals("0")){
                            show();
                            break;
                        }
                        if (userService.existsByEmail(saveEmail)) {
                            System.out.println("\n[!] Error: This email already exists");
                            System.out.println("[!] You can enter 0 to cancel this program");
                            continue;
                        }
                        String savePassword = "";
                        if(console != null) {
                            char[] passwordArray = console.readPassword("Enter your password : ");
                            savePassword = new String(passwordArray);
                        } else {
                            System.out.print("Enter your password : ");
                            savePassword = sc.next();
                        }
                        System.out.print("Are you admin (yes/no) : ");
                        while (!sc.hasNext("(?i)Yes|(?i)No")) {
                            sc.next();
                        }

                        String input = sc.next();
                        boolean isAdmin = false;
                        if (input.equalsIgnoreCase("yes")) {
                            isAdmin = true;
                            userService.register(name, saveEmail, savePassword, isAdmin);
                            userService.login(saveEmail, savePassword);
                            checkIsAdmin++;
                            adminUI.show();
                            break;
                        }

                        userService.register(name, saveEmail, savePassword, isAdmin);
                        userService.login(saveEmail, savePassword);
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
        if (checkIsAdmin==0) {
            semiMenu();
        }

    }

    public void semiMenu() {
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
            if(currentUser.getRestaurantId() == null) {
                System.out.println("[!] You are not a restaurant owner, create a restaurant to view this menu.");
                System.out.println("Going back to main menu...");
                semiMenu();
            } else {
                restaurantUI.show();
            }
        } else {
            userService.logout();
            System.out.println("----------------------------------");
            System.out.println("\t Logout successful");
            System.out.println("----------------------------------");
            show();
        }
    }
}