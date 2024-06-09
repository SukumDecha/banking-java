package me.sit.dev.ui.impl;

import me.sit.dev.service.ServiceFactory;
import me.sit.dev.ui.BaseUI;

import java.util.Scanner;

public class AdminUI extends BaseUI {

    private LoginUI loginUI;
    private Scanner sc = new Scanner(System.in);

    public AdminUI(ServiceFactory serviceFactory) {
        super("Client UI", "This UI only shows the client's view.", serviceFactory);
    }

    @Override
    public void show() {
        try {
            System.out.println("\n---------------------------------------");
            System.out.println("\t\t\t   Admin UI");
            System.out.print("---------------------------------------");
            showAdminMenu();
        } catch (Exception e) {
            System.out.println("An error occurred while showing the UI: " + e.getMessage());
        }
    }

    public void showAdminMenu() {
        System.out.println("\n\t\t\t--- Admin Menu ---\n");
        System.out.println("1. Add a new restaurant");
        System.out.println("2. Update a restaurant");
        System.out.println("3. Delete a restaurant");
        System.out.println("4. Show all restaurants");
        System.out.println("5. Exit from program");
        System.out.print("[!] Please enter 1|2|3|4|5 : ");
        while (!sc.hasNext("[1|2|3|4|5]")) {
            System.out.print("[!] Please try again. (Enter 1|2|3|4|5) : ");
            sc.next();
        }
        int selected = sc.nextInt();
        switch (selected) {
            case 1:
                addRestaurant();
                break;
            case 2:
                updateRestaurant();
                break;
            case 3:
                deleteRestaurant();
                break;
            case 4:
                showAllRestaurants();
                break;
            case 5:
                System.out.println("Exiting from the program...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input. Please try again.");
                showAdminMenu();
        }
    }

    public void addRestaurant() {
        System.out.println("Add a new restaurant method");
    }

    public void updateRestaurant() {
        System.out.println("Update a restaurant method");
    }

    public void deleteRestaurant() {
        System.out.println("Delete a restaurant method");
    }

    public void showAllRestaurants() {
        System.out.println("Show all restaurants method");
    }

}
