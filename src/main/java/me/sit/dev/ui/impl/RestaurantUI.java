package me.sit.dev.ui.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.Session;
import me.sit.dev.service.ServiceFactory;
import me.sit.dev.ui.BaseUI;

import java.util.Scanner;

public class RestaurantUI extends BaseUI {

    private Scanner sc = new Scanner(System.in);

    private static String Program_prompt = """
                                
            -------------- MAIN MENU --------------
                          1. Add Food         
                          2. Edit Food
                        3. Show All Food
                        4. Show history
                           5. Exit
                     6. Delete Restaurant
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
                    addFood();
                    break;
                case 2:
                    editFood();
                    break;
                case 3:
                    showAllFood();
                    break;
                case 4:
                    showHistory();
                    break;
                case 5:
                    System.out.println("Exit");
                    program_status = false;
                    break;
                case 6:
                    deleteRestaurant();
                    break;
            }
        }
    }

    private void addFood() {
        String restaurantId = Session.getCurrentSession().getRestaurantId();

        try {
            System.out.println("Enter new product name : ");
            String name = sc.next();
            System.out.println("Enter price (only number) : ");
            while (!sc.hasNextDouble()){
                System.out.print("Please tyr again (input number) : ");
                sc.next();
            }
            double price = sc.nextDouble();
            System.out.println("Enter quantity : ");
            while (!sc.hasNextInt()){
                System.out.print("Please tyr again (input number) : ");
                sc.next();
            }
            int quantity = sc.nextInt();
            productService.addProduct(restaurantId, name, price, quantity);
            System.out.println("Food added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding food: " + e.getMessage());
        }
    }

    private void editFood() {
        String restaurantId = Session.getCurrentSession().getRestaurantId();

        try {
            System.out.println("Enter the product ID you want to edit: ");
            String productId = sc.next();
            Product product = productService.findById(restaurantId, productId);

            if (product != null) {
                System.out.println("Current name: " + product.getName());
                System.out.println("Enter new name (or press enter to keep current): ");
                sc.nextLine();  // Consume newline left-over
                String newName = sc.nextLine();
                if (!newName.isBlank()) {
                    product.setName(newName);
                }

                System.out.println("Current price: " + product.getPrice());
                System.out.println("Enter new price (or press enter to keep current): ");
                while (!sc.hasNextDouble()) {
                    System.out.println("Please enter a valid number");
                    sc.next();
                }

                double newPriceInput = sc.nextDouble();
                product.setPrice(newPriceInput);

                System.out.println("Current quantity: " + product.getQuantity());
                System.out.println("Enter new quantity (or press enter to keep current): ");

                while (!sc.hasNextInt()) {
                    System.out.println("Please enter a valid quantity");
                    sc.next();
                }

                int newQuantity = sc.nextInt();
                product.setQuantity(newQuantity);

                productService.updateProduct(restaurantId, productId, product);
                System.out.println("Food updated successfully!");
            } else {
                System.out.println("Product not found!");
            }
        } catch (Exception e) {
            System.err.println("Error editing food: " + e.getMessage());
        }
    }

    private void showAllFood() {
        String restaurantId = Session.getCurrentSession().getRestaurantId();
        try {
            restaurantService.showAllProducts(restaurantId);
        } catch (Exception e) {
            System.err.println("Error showing all food: " + e.getMessage());
        }
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
        System.out.println("Are you sure to remove restaurant? (Yes/No)");
        String confirmation = sc.next();
        if (confirmation.equalsIgnoreCase("Yes") || confirmation.equalsIgnoreCase("Y")) {
            try {
                String restaurantId = Session.getCurrentSession().getRestaurantId();
                restaurantService.deleteRestaurant(restaurantId);
                System.out.println("Restaurant removed successfully!");
            } catch (Exception e) {
                System.err.println("Error deleting restaurant: " + e.getMessage());
            }
        } else {
            System.out.println("Restaurant removal cancelled.");
        }
    }
}
