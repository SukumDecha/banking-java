package me.sit.dev.ui.impl;

import me.sit.dev.entity.impl.Session;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.service.ServiceFactory;
import me.sit.dev.ui.BaseUI;

import java.util.Scanner;

public class RestaurantUI extends BaseUI {

    private Scanner sc = new Scanner(System.in);

    private static String Program_prompt = """
                                
            -------------- MAIN MENU --------------
                          1. add food         
                          2. edit food
                        3. show all food
                           4. exit
                       5. deleteRestaurant
            ---------------------------------------       
            """;

    public RestaurantUI(ServiceFactory serviceFactory) {
        super("Restaurant UI", "This UI only shows the restaurant's view.", serviceFactory);
    }

    @Override
    public void show() {
        System.out.println("Restaurant UI");
        System.out.println(Program_prompt);
        System.out.print("Choose your program : ");
        while (!sc.hasNext("[1|2|3|4|5|6]")) {
            System.out.print("please try again [select 1,2,3,4,5] : ");
            sc.next();
        }
        boolean program_status = true;
        int count = 1;
        int programSelected;
        while (program_status) {
            if (count != 1) {
                System.out.println(Program_prompt);
                System.out.print("Choose next program : ");
                while (!sc.hasNext("[1|2|3|4|5|6]")) {
                    System.out.print("please try again [select 1,2,3,4,5,6] : ");
                    sc.next();
                }
            }
            programSelected = sc.nextInt();
            count++;
            switch (programSelected) {
                case 1:
                    System.out.println("add food");
                    continue;
                case 2:
                    System.out.println("edit food");
                    continue;
                case 3:
                    System.out.println("show all food");
                    continue;
                case 4:
                    System.out.println("exit");
                    program_status = false;
                    break;
                case 5:
                    System.out.println("deleteRestaurant");
                    program_status = false;
                    break;
            }
        }
    }

    private void addFood() {
        String restaurantId = Session.getCurrentSession().getRestaurantId();

        System.out.println("Enter new product name : ");
        String name = sc.next();
        System.out.println("Enter price (only number) : ");
        int price = sc.nextInt();
        productService.addProduct(restaurantId, name, price, 0);
    }

    private void editFood() {
        
    }

    private void showAllFood() {
        String restaurantId = Session.getCurrentSession().getRestaurantId();

        restaurantService.showAllProducts(restaurantId);
    }

    private void showHistory(){
        System.out.println("Show all product and amount that is ordered");
        String restaurantId = Session.getCurrentSession().getRestaurantId();
        Restaurant restaurant = restaurantService.findById(restaurantId);

        if (restaurant != null) {
            int maxPage = productService.findAll(restaurantId).size() / 5;
            System.out.println("Enter page number to view (max page = " + maxPage + "): ");
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid page number");
                sc.next();
            }
            int page = sc.nextInt();

            restaurantService.showOrderPagination(restaurantId, page, 5);;
        } else {
            System.out.println("Restaurant not found!");
        }
    }

    private void deleteRestaurant() {
        System.out.println("Are you sure to remove restaurant");
        sc.next();
        while (!sc.hasNext("(?i)Yes|(?i)No|(?i)y|(?i)n")) {
            System.out.println("please try again");
            sc.next();
        }
        if (sc.hasNext("(?i)[y].*")) {
            restaurantService.deleteRestaurant("0");
        }
        if (sc.hasNext("(?i)[n].*")) {
            System.out.println("Im not sure to remove");
        }
    }

}
