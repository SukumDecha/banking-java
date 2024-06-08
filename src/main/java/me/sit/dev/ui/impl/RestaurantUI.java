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
        Restaurant restaurant = currentUser.getRestaurant();

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
        Restaurant restaurant = currentUser.getRestaurant();
        String restaurantId = restaurant.getId();

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
                System.out.print("Please try again (input number) : ");
                sc.next();
            }
            int quantity = sc.nextInt();
            Product product = productService.addProduct(restaurantId, name, price, quantity);
            restaurant.getProducts().add(product);

            restaurantService.updateRestaurant(restaurantId, restaurant);
            userService.update(restaurantId, currentUser);
            System.out.println("Food added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding food: " + e.getMessage());
        }
    }

    private void editFood() {
        User currentUser = Session.getCurrentSession().getUser();
        Restaurant restaurant = currentUser.getRestaurant();
        String restaurantId = restaurant.getId();

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

                restaurant.getProducts().add(product);
                restaurantService.updateRestaurant(restaurantId, restaurant);
                userService.update(currentUser.getId(), currentUser);
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
        String restaurantId = currentUser.getRestaurant().getId();

        try {
            restaurantService.showAllProducts(restaurantId);
        } catch (Exception e) {
            System.err.println("Error showing all food: " + e.getMessage());
        }
    }

    private void showHistory(){
        System.out.println("Show all product and amount that is ordered");
        User currentUser = Session.getCurrentSession().getUser();
        Restaurant restaurant = currentUser.getRestaurant();
        String restaurantId = restaurant.getId();

        int maxPage = productService.findAll(restaurantId).size() / 5;
        System.out.println("Enter page number to view (max page = " + maxPage + "): ");
        while (!sc.hasNextInt()) {
            System.out.println("Please enter a valid page number");
            sc.next();
        }
        int page = sc.nextInt();

        restaurantService.showOrderPagination(restaurantId, page, 5);;
    }

    private void deleteRestaurant() {
        User currentUser = Session.getCurrentSession().getUser();
        String restaurantId = currentUser.getRestaurant().getId();

        System.out.println("Are you sure to remove restaurant? (Yes/No)");
        String confirmation = sc.next();
        if (confirmation.equalsIgnoreCase("Yes") || confirmation.equalsIgnoreCase("Y")) {
            try {
                currentUser.setRestaurant(null);

                for(Product product : productService.findAll(restaurantId)){
                    productService.deleteProduct(restaurantId, product.getId());
                }

                restaurantService.deleteRestaurant(restaurantId);
                userService.update(currentUser.getId(), currentUser);
                System.out.println("Restaurant removed successfully!");
            } catch (Exception e) {
                System.err.println("Error deleting restaurant: " + e.getMessage());
            }
        } else {
            System.out.println("Restaurant removal cancelled.");
        }
    }
}
