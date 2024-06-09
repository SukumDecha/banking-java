package me.sit.dev.ui.impl;

import me.sit.dev.service.ServiceFactory;
import me.sit.dev.ui.BaseUI;

import java.util.Scanner;

public class AdminUI extends BaseUI {

    private LoginUI loginUI;
    private Scanner sc = new Scanner(System.in);
    private final String selectEntityUIPrompt = """
                                
            --- Select Entity to edit ---
                 1. User
                 2. Resturant
                 3. logout
            ---------------------------- 
       """;
    private final String editingUserUIPrompt = """
                                
            ----- Editing User ----
               1. List all user
               2. Edit user
               3. Delete user
               4. Go back
            -----------------------
            """;
    private final String editingRestaurantUIPrompt = """
                                
            ------- Editing Restaurant ----------
                      1. Edit name
                      2. Edit product
                      3. Remove product
                      4. delete restaurant
                      5. Go back
            -------------------------------------
            """;
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
        try {
            System.out.println(selectEntityUIPrompt);
            System.out.print("Enter your choice : ");
            while (!sc.hasNext("[1|2|3]")) {
                System.out.print("please try again [select 1,2,3] : ");
                sc.next();
            }
            String entityChoice=sc.next();
            while (true){
                switch (entityChoice){
                    case "1" :
                        editingUser();
                        continue;
                    case "2" :
                        editingRestaurant();
                        continue;
                    case "3" :
                        userService.logout();
                        System.out.println("----------------------------------");
                        System.out.println("\t\t Logout successful");
                        System.out.println("----------------------------------");
                        entityChoice="0";
                        loginUI.show();
                        break;
                }
            }

        }catch (Exception e) {
            System.out.println("An error occurred while showing admin menu: " + e.getMessage());
        }

    }

    public void editingUser(){
        try {
            System.out.println(editingUserUIPrompt);
            System.out.print("Enter your choice : ");
            while (!sc.hasNext("[1|2|3|4]")) {
                System.out.print("please try again [select 1,2,3,4] : ");
                sc.next();
            }
            int count = 1;
            int editUserSelected;
            boolean editUserStatus=true;
            while (editUserStatus){
                if (count != 1) {
                    System.out.println(editingUserUIPrompt);
                    System.out.print("Choose next program : ");
                    while (!sc.hasNext("[1|2|3|4]")) {
                        System.out.print("please try again [select 1,2,3,4] : ");
                        sc.next();
                    }
                }
                editUserSelected = sc.nextInt();
                count++;
                switch (editUserSelected){
                    case 1:
                        System.out.println("------ List all user ------");
                        continue;
                    case 2:
                        System.out.println("------ Edit user ------");
                        continue;
                    case 3:
                        System.out.println("------ Delete user ------");
                        continue;
                    case 4:
                        System.out.println("------ go back ------");
                        editUserStatus=false;
                        showAdminMenu();
                        break;
                }
            }
        }catch (Exception e) {
            System.out.println("An error occurred while showing editingUser: " + e.getMessage());
        }
    }
    public void editingRestaurant(){
        try {
            System.out.println(editingRestaurantUIPrompt);
            System.out.print("Enter your choice : ");
            while (!sc.hasNext("[1|2|3|4|5]")) {
                System.out.print("please try again [select 1,2,3,4,5] : ");
                sc.next();
            }
            int count = 1;
            int editRestaurantSelected;
            boolean editRestaurantStatus=true;
            while (editRestaurantStatus){
                if (count != 1) {
                    System.out.println(editingRestaurantUIPrompt);
                    System.out.print("Choose next program : ");
                    while (!sc.hasNext("[1|2|3|4|5]")) {
                        System.out.print("please try again [select 1,2,3,4,5] : ");
                        sc.next();
                    }
                }
                editRestaurantSelected = sc.nextInt();
                count++;
                switch (editRestaurantSelected){
                    case 1:
                        System.out.println("------ Edit name ------");
                        continue;
                    case 2:
                        System.out.println("------ Edit product ------");
                        continue;
                    case 3:
                        System.out.println("------ Remove product ------");
                        continue;
                    case 4:
                        System.out.println("------ delete restaurant ------");
                        break;
                    case 5:
                        System.out.println("------ go back ------");
                        editRestaurantStatus=false;
                        showAdminMenu();
                        break;
                }
            }
        }catch (Exception e) {
            System.out.println("An error occurred while showing editingRestaurant: " + e.getMessage());
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
