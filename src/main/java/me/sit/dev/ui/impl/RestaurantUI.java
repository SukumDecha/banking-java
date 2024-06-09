package me.sit.dev.ui.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.Session;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.service.ServiceFactory;
import me.sit.dev.ui.BaseUI;

import java.util.Scanner;

public class RestaurantUI extends BaseUI {

    private Scanner sc = new Scanner(System.in);
    private LoginUI loginUI;

    private static String Program_prompt = """
                                
            -------------- MAIN MENU --------------
                          1. Add Food         
                          2. Edit Food
                        3. Show All Food
                        4. Show history
                     5. Delete Restaurant
                        6. Go back
            ---------------------------------------       
            """;

    public RestaurantUI(ServiceFactory serviceFactory) {
        super("Restaurant UI", "This UI only shows the restaurant's view.", serviceFactory);
    }

    public void setLoginUI(LoginUI loginUI) {
        this.loginUI = loginUI;
    }

    @Override
    public void show() {
        User currentUser = Session.getCurrentSession().getUser();
        Session.getCurrentSession().setRestaurantId(currentUser.getRestaurantId());

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
                    deleteRestaurant();
                    break;
                case 6:
                    System.out.println("Going back to main menu");
                    loginUI.semiMenu();
                    break;
            }
        }
    }

    private void addFood() {
        User currentUser = Session.getCurrentSession().getUser();
        String restaurantId = currentUser.getRestaurantId();

        try {
            System.out.println("Enter new product name : ");
            String name = sc.next();
            System.out.println("Enter price (only number) : ");
            double price = 0;
            while (true) {
                while (!sc.hasNextDouble()) {
                    System.out.print("Please try again (input number) : ");
                    sc.next();
                }

                price = sc.nextDouble();
                if (price <= 0) {
                    System.out.println("Price must be greater than 0. Please enter a valid price.");
                } else {
                    break;
                }
            }
            System.out.println("Enter quantity : ");
            int quantity = 0;
            while (true) {
                while (!sc.hasNextDouble()) {
                    System.out.print("Please try again (input number) : ");
                    sc.next();
                }

                quantity = sc.nextInt();
                if (quantity < 0) {
                    System.out.println("Quantity must be equals or greater than 0. Please enter a valid amount.");
                } else {
                    break;
                }
            }

            Product product = productService.addProduct(restaurantId, name, price, quantity);
            Restaurant restaurant = restaurantService.findById(restaurantId);
            restaurant.addProduct(product);

            restaurantService.updateRestaurant(restaurantId, restaurant);
            System.out.println("Food added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding food: " + e.getMessage());
        }
    }

    private void editFood() {
        User currentUser = Session.getCurrentSession().getUser();
        String restaurantId = currentUser.getRestaurantId();

        try {
            showAllFood();
            System.out.println("Enter the product ID you want to edit: ");
            String productId = sc.next();
            Product product = productService.findById(restaurantId, productId);

            if (product != null) {
                System.out.println("Current name: " + product.getName());
                System.out.print("Enter new name (or press enter to keep current): ");
                Scanner editSc = new Scanner(System.in);

                if (editSc.hasNextLine()) {
                    String newName = editSc.nextLine();
                    if (!newName.isEmpty()) {
                        product.setName(newName);
                    }
                }

                System.out.println("\nCurrent quantity: " + product.getQuantity());
                System.out.println("Enter new quantity (or press enter to keep current): ");
                int newQuantity;
                while (true) {
                    if (editSc.hasNextLine()) {
                        String newQuantityInput = editSc.nextLine();

                        if (newQuantityInput.isEmpty()) {
                            break;
                        }

                        try {
                            newQuantity = Integer.parseInt(newQuantityInput);
                            if (newQuantity < 0) {
                                System.out.println("Quantity must be a non-negative number. Please enter a valid quantity.");
                            } else {
                                product.setQuantity(newQuantity);
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number for the quantity.");
                            System.out.print("Try again : ");
                        }
                    }
                }

                System.out.println("\nCurrent price: " + product.getPrice());
                System.out.print("Enter new price (or press enter to keep current): ");
                double newPrice;
                while (true) {
                    if (editSc.hasNextLine()) {
                        String newPriceInput = editSc.nextLine();
                        if(newPriceInput.isEmpty()){
                            break;
                        }
                        try {
                            newPrice = Double.parseDouble(newPriceInput);
                            if (newPrice <= 0) {
                                System.out.println("Price must be greater than 0. Please enter a valid price.");
                            } else {
                                product.setPrice(newPrice);
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number for the price.");
                            System.out.print("Try again : ");
                        }
                    }
                }


                productService.updateProduct(restaurantId, productId, product);

                Restaurant restaurant = restaurantService.findById(restaurantId);
                restaurant.updateProduct(product);

                restaurantService.updateRestaurant(restaurantId, restaurant);
                System.out.println("Food updated successfully!");
            } else {
                System.out.println("Product not found!");
            }
        } catch (Exception e) {
            System.err.println("Error editing food: " + e.getMessage());
        }
    }

    private void showAllFood() {
        User currentUser = Session.getCurrentSession().getUser();
        String restaurantId = currentUser.getRestaurantId();

        try {
            restaurantService.showAllProducts(restaurantId);
        } catch (Exception e) {
            System.err.println("Error showing all food: " + e.getMessage());
        }
    }

    private void showHistory() {
        System.out.println("------------ Order History ------------ ");
        User currentUser = Session.getCurrentSession().getUser();
        String restaurantId = currentUser.getRestaurantId();

        int maxPage = restaurantService.showOrderPagination(restaurantId, 1, 5);

        while (true) {

            System.out.println("Enter page number or 0 to go back: ");
            int page = sc.nextInt();
            if (page == 0) {
                break;
            } else if (page < 1 || page > maxPage) {
                System.out.println("Invalid page number. Please enter a valid page number.");
            } else {
                restaurantService.showOrderPagination(restaurantId, page, 5);
            }
        }
    }

    private void deleteRestaurant() {
        User currentUser = Session.getCurrentSession().getUser();
        String restaurantId = currentUser.getRestaurantId();

        System.out.println("Are you sure to remove restaurant? (Yes/No)");
        String confirmation = sc.next();
        if (confirmation.equalsIgnoreCase("Yes") || confirmation.equalsIgnoreCase("Y")) {
            try {
                currentUser.setRestaurantId(null);

                for (Product product : productService.findAll(restaurantId)) {
                    productService.deleteProduct(restaurantId, product.getId());
                }

                restaurantService.deleteRestaurant(restaurantId);
                userService.update(currentUser.getId(), currentUser);
                System.out.println("Restaurant removed successfully!");
                loginUI.semiMenu();
            } catch (Exception e) {
                System.err.println("Error deleting restaurant: " + e.getMessage());
            }
        } else {
            System.out.println("Restaurant removal cancelled.");
        }
    }
}