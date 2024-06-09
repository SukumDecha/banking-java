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
        while (true) {
            System.out.println("--- Select Entity to edit ---");
            System.out.println("1. User");
            System.out.println("2. Restaurant");
            System.out.println("----------------------------");
            System.out.print("Enter your choice: ");
            int entityChoice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (entityChoice) {
                case 1:
                    editUser(sc);
                    break;
                case 2:
                    editRestaurant(sc);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
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