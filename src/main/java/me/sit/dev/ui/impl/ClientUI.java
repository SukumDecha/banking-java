package me.sit.dev.ui.impl;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.Session;
import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.service.ServiceFactory;
import me.sit.dev.ui.BaseUI;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class ClientUI extends BaseUI {

    private LoginUI loginUI;

    private Scanner sc = new Scanner(System.in);

    private final String programPrompt = """
                                
            -------------- MAIN MENU --------------
                        1. Create Restaurant          
                        2. Order Food
                        3. Go back
            ---------------------------------------       
            """;
    private final String orderUIPrompt = """
                                
            -------------- Order UI --------------
                       1. Order food     
                       2. Search food   
                       3. See your cart     
                       4. Back to main menu
            ---------------------------------------       
            """;
    private final String selectMenuPrompt = """
                    
            -------------- Select Food Menu --------------
                         1. Select food      
                         2. Back to OrderUI
            ----------------------------------------------       
            """;

    private final String cartMenuPrompt = """
                    
            --------------- Cart Menu ------------------
                       1. Show cart info
                       2. Edit cart
                       3. Clear cart
                       4. Check-out (Pay-bill)
                       5. Back to Select Food Menu
            ---------------------------------------------
            """;

    private final String editCartMenuPrompt = """
                    
            -------------- Edit Cart Menu --------------
                         1. Edit amount
                         2. Remove from cart
                         3. Back to Cart Menu
            ---------------------------------------------
            """;

    public ClientUI(ServiceFactory serviceFactory) {
        super("Client UI", "This UI only shows the client's view.", serviceFactory);
    }

    public void setLoginUI(LoginUI loginUI) {
        this.loginUI = loginUI;
    }

    @Override
    public void show() {
        try {
            System.out.println("\n---------------------------------------");
            System.out.println("\t\t\t   Client UI");
            System.out.print("---------------------------------------");
            showMainMenu();
        } catch (Exception e) {
            System.out.println("An error occurred while showing the UI: " + e.getMessage());
        }
    }

    private boolean showAllRestaurants() {
        try {
            Collection<Restaurant> restaurants = restaurantService.findAll();
            if (restaurants.isEmpty()) {
                System.out.println("No restaurants available.");
                return false;
            }
            System.out.println("------ Available restaurants ------");
            int count = 1;
            for (Restaurant restaurant : restaurants) {
                System.out.println("\t\t" + count + ". " + restaurant.getName());
                count++;
            }
            System.out.println("-----------------------------------");
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred while fetching restaurants: " + e.getMessage());
            return false;
        }
    }

    private void selectRestaurant() {
        try {
            List<Restaurant> restaurants = restaurantService.findAll().stream().toList();

            System.out.print("Please select a restaurant by number : ");
            while (true) {
                while (!sc.hasNextInt()) {
                    System.out.print("Invalid selection. Please try again: ");
                    sc.next();
                }

                int restaurantIndex = sc.nextInt() - 1;

                if (restaurantIndex >= 0 && restaurantIndex < restaurants.size()) {
                    String currentRestaurantId = restaurants.get(restaurantIndex).getId();
                    Session.getCurrentSession().setRestaurantId(currentRestaurantId);
                    break;
                }
                System.out.println("Invalid selection. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while selecting a restaurant: " + e.getMessage());
        }
    }

    private void showMainMenu() {
        User currentUser = Session.getCurrentSession().getUser();
        try {
            System.out.println(programPrompt);
            System.out.print("Choose your program : ");
            while (!sc.hasNext("[1|2|3]")) {
                System.out.print("please try again [select 1,2,3] : ");
                sc.next();
            }
            boolean program_status = true;
            int count = 1;
            int programSelected;
            while (program_status) {
                if (count != 1) {
                    System.out.println(programPrompt);
                    System.out.print("Choose next program : ");
                    while (!sc.hasNext("[1|2|3]")) {
                        System.out.print("please try again [select 1,2,3] : ");
                        sc.next();
                    }
                }
                programSelected = sc.nextInt();
                count++;
                switch (programSelected) {
                    case 1:
                        if (currentUser.getRestaurant() != null) {
                            System.out.println("You already have a restaurant.");
                            break;
                        }
                        createRestaurant();
                        continue;
                    case 2:
                        orderUI();
                        continue;
                    case 3:
                        loginUI.semiMenu();
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while showing the main menu: " + e.getMessage());
        }
    }

    public void createRestaurant() {
        try {
            User currentUser = Session.getCurrentSession().getUser();
            String ownerId = currentUser.getId();

            System.out.println("Enter restaurant name : ");
            String restaurantName = sc.next();
            Restaurant restaurant = restaurantService.addRestaurant(ownerId, restaurantName);

            currentUser.setRestaurant(restaurant);
            userService.update(currentUser.getId(), currentUser);
            System.out.println("Restaurant created successfully.");

        } catch (Exception e) {
            System.out.println("An error occurred while creating a restaurant: " + e.getMessage());
        }
    }

    public void orderUI() {
        try {
            if (!showAllRestaurants()) {
                return;
            }
            selectRestaurant();
            System.out.println(orderUIPrompt);
            System.out.print("Select your order : ");
            while (!sc.hasNext("[1|2|3|4]")) {
                System.out.print("please try again [select 1,2,3,4] : ");
                sc.next();
            }
            boolean orderstatus = true;
            int orderCount = 1;
            int orderSelected;
            while (orderstatus) {
                if (orderCount != 1) {
                    System.out.println(orderUIPrompt);
                    System.out.print("Select your next order : ");
                    while (!sc.hasNext("[1|2|3|4]")) {
                        System.out.print("please try again [select 1|2|3|4] : ");
                        sc.next();
                    }
                }
                orderCount++;
                orderSelected = sc.nextInt();
                switch (orderSelected) {
                    case 1:
                        System.out.println("------- order food method -------");
                        orderFood();
                        continue;
                    case 2:
                        searchFood();
                        continue;
                    case 3:
                        inMyCart();
                        continue;
                    case 4:
                        System.out.println("Back to main menu");
                        orderstatus = false;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred in the order UI: " + e.getMessage());
        }
    }

    public void orderFood() {
        try {
            String currentRestaurantId = Session.getCurrentSession().getRestaurantId();
            while (true) {
                showAllProducts();
                Scanner scFood = new Scanner(System.in);
                System.out.print("Select menu : ");
                while (!scFood.hasNextInt()) {
                    System.out.println("you can enter 0 to end this process");
                    System.out.print("please try again (input number): ");
                    scFood.next();
                }
                if (scFood.hasNext("0")) {
                    break;
                }
                int productIndex = scFood.nextInt() - 1;
                List<Product> products = productService.findAll(currentRestaurantId);
                if (productIndex >= 0 && productIndex < products.size()) {
                    Product selectedProduct = products.get(productIndex);
                    Session.getCurrentSession().setSelectingProduct(selectedProduct);
                    addToCart();
                    break;
                } else {
                    System.out.println("Invalid selection.");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while ordering food: " + e.getMessage());
        }
    }

    public void searchFood() {
        try {
            Scanner scSearch = new Scanner(System.in);
            System.out.print("Enter the food name to search : ");
            String searchName = scSearch.nextLine();
            Collection<Product> products = productService.searchByName(searchName);

            if (products.isEmpty()) {
                System.out.println("No products found.");
                return;
            }
            int count = 1;
            System.out.println("---------- Search results ----------");
            System.out.println("ID | Name | Price | Quantity");
            System.out.println("-----------------------------");
            for (Product product : products) {
                System.out.printf("%-11s | %-7s | %-12d| %-6f %n", product.getId(), product.getName(), product.getQuantity(), product.getPrice());
                count++;
            }
            System.out.println("-----------------------------");
        } catch (Exception e) {
            System.out.println("An error occurred while searching for food: " + e.getMessage());
        }
    }

    public void inMyCart() {
        try {
            boolean statusCart = true;
            while (statusCart) {
                User currentUser = Session.getCurrentSession().getUser();
                Scanner scIncart = new Scanner(System.in);
                System.out.println(cartMenuPrompt);
                System.out.print("Select menu : ");
                while (!scIncart.hasNext("[1|2|3|4|5]")) {
                    System.out.println("please try again [select 1|2|3|4|5] : ");
                    scIncart.next();
                }
                int cartSelected = scIncart.nextInt();
                switch (cartSelected) {
                    case 1:
                        System.out.println("\n  ---- Show cart info ----");
                        cartService.showCartDetails(currentUser);
                        break;
                    case 2:
                        editCart();
                        break;
                    case 3:
                        cartService.clearCart(currentUser);
                        System.out.println("Your cart has been cleared.");
                        break;
                    case 4:
                        if(currentUser.getCart().getProducts().isEmpty()){
                            System.out.println("Your cart is empty. Please add some products first.");
                            break;
                        }

                        Restaurant currentRestaurant = restaurantService.findById(Session.getCurrentSession().getRestaurantId());
                        orderService.createOrder(currentUser, currentRestaurant);

                        restaurantService.updateRestaurant(currentRestaurant.getId(), currentRestaurant);
                        userService.update(currentUser.getId(), currentUser);
                        statusCart = false;
                        break;
                    case 5:
                        statusCart = false;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while managing the cart: " + e.getMessage());
        }
    }

    private void addToCart() {
        try {
            User currentUser = Session.getCurrentSession().getUser();
            Product currentProduct = Session.getCurrentSession().getSelectingProduct();

            System.out.print("Enter the quantity: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number: ");
                sc.next();
            }

            int quantity = sc.nextInt();
            if (quantity > 0) {
                Restaurant currentRestaurant = restaurantService.findById(Session.getCurrentSession().getRestaurantId());

                int productQuantity = productService.getQuantity(currentRestaurant.getId(), currentProduct.getId());
                if (quantity > productQuantity) {
                    System.out.println("---------------------------------------");
                    System.out.println("\tNot enough stock. Try again.");
                    System.out.println("---------------------------------------");
                    showAllProducts();
                    return;
                }

                cartService.addToCart(currentUser, currentRestaurant, currentProduct, quantity);
                System.out.println();
                orderService.showOrderDetails(new Order(currentUser, currentUser.getCart(),
                        currentRestaurant.getId(), currentProduct.getName()), false);
                System.out.println("Added to cart.");
            } else {
                System.out.println("Invalid quantity. Try again.");
                addToCart();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while adding to the cart: " + e.getMessage());
        }
    }

    private void editCart() {
        try {
            Scanner scEditCart = new Scanner(System.in);
            boolean statusEditCart = true;
            while (statusEditCart) {
                System.out.println(editCartMenuPrompt);
                System.out.print("Select edit menu : ");
                while (!scEditCart.hasNext("[1|2|3]")) {
                    System.out.print("please try again [select 1,2,3] : ");
                    scEditCart.next();
                }
                int editSelected = scEditCart.nextInt();
                switch (editSelected) {
                    case 1:
                        editCartAmount();
                        break;
                    case 2:
                        removeFromCart();
                        break;
                    case 3:
                        statusEditCart = false;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while editing the cart: " + e.getMessage());
        }
    }

    private void editCartAmount() {
        try {
            User currentUser = Session.getCurrentSession().getUser();
            List<Product> cartProducts = currentUser.getCart().getProducts().keySet().stream().toList();

            if (cartProducts.isEmpty()) {
                System.out.println("!!!!  Your cart is empty.  !!!!");
                return;
            }

            showAllProductsInCart();
            Scanner scEditAmount = new Scanner(System.in);
            System.out.print("\nSelect the product to edit amount: ");
            while (!scEditAmount.hasNextInt()) {
                System.out.print("Invalid selection. Please try again: ");
                scEditAmount.next();
            }
            int productIndex = scEditAmount.nextInt() - 1;
            if (productIndex >= 0 && productIndex < cartProducts.size()) {
                Product selectedProduct = cartProducts.get(productIndex);
                System.out.print("Enter the new amount: ");
                while (!scEditAmount.hasNextInt()) {
                    System.out.print("Invalid input. Please enter a number: ");
                    scEditAmount.next();
                }
                int newAmount = scEditAmount.nextInt();
                if (newAmount > 0) {
                    int productQuantity = productService.getQuantity(currentUser.getRestaurant().getId(), selectedProduct.getId());
                    if (newAmount > productQuantity) {
                        System.out.println("Not enough stock. Try again.");
                        editCartAmount();
                        return;
                    }
                    cartService.updateCart(currentUser, selectedProduct, newAmount);
                    System.out.println("Amount updated.");
                } else {
                    System.out.println("Invalid amount. Try again.");
                    editCartAmount();
                }
            } else {
                System.out.println("Invalid selection. Try again.");
                editCartAmount();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while editing the cart amount: " + e.getMessage());
        }
    }

    private void removeFromCart() {
        try {
            User currentUser = Session.getCurrentSession().getUser();
            List<Product> cartProducts = currentUser.getCart().getProducts().keySet().stream().toList();
            if (cartProducts.isEmpty()) {
                System.out.println("Your cart is empty.");
                return;
            }

            showAllProductsInCart();
            Scanner scRemoveProduct = new Scanner(System.in);
            System.out.print("\nSelect the product to remove from your card:");
            while (!scRemoveProduct.hasNextInt()) {
                System.out.print("Invalid selection. Please try again: ");
                scRemoveProduct.next();
            }
            int productIndex = scRemoveProduct.nextInt() - 1;
            if (productIndex >= 0 && productIndex < cartProducts.size()) {
                Product selectedProduct = cartProducts.get(productIndex);
                cartService.removeFromCart(currentUser, selectedProduct);
                System.out.println("Product removed from cart.");
            } else {
                System.out.println("Invalid selection. Try again.");
                removeFromCart();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while removing the product from the cart: " + e.getMessage());
        }
    }

    private void showAllProducts() {
        try {
            String currentRestaurantId = Session.getCurrentSession().getRestaurantId();
            Collection<Product> products = productService.findAll(currentRestaurantId);
            if (products.isEmpty()) {
                System.out.println("\nNo products available.\n");
                return;
            }
            System.out.println("\n------ Available products ------");
            int count = 1;
            for (Product product : products) {
                StringBuilder str = new StringBuilder();
                str.append(count).append(". ").append(product.getName())
                        .append(" | Price : ").append(product.getPrice())
                        .append(" | Quantity : ").append(productService.getQuantity(currentRestaurantId, product.getId()));
                System.out.println(str);
                count++;
            }
            System.out.println("---------------------------------");
        } catch (Exception e) {
            System.out.println("An error occurred while showing all products: " + e.getMessage());
        }
    }

    private void showAllProductsInCart() {
        try {
            User currentUser = Session.getCurrentSession().getUser();
            List<Product> cartProducts = currentUser.getCart().getProducts().keySet().stream().toList();
            if (cartProducts.isEmpty()) {
                System.out.println("Your cart is empty.");
                return;
            }
            System.out.println("Products in your cart:");
            System.out.println("------------------------------------------------");
            System.out.println("Id | Name    |  Quantity   | Price");
            System.out.println("------------------------------------------------");
            int count = 1;
            for (Product product : cartProducts) {
                StringBuilder str = new StringBuilder();
                str.append(count).append(". ").append(product.getName())
                        .append(" | Price : ").append(product.getPrice())
                        .append(" | Quantity : ").append(currentUser.getCart().getProducts().get(product));
                System.out.println(str);
                count++;

            }
        } catch (Exception e) {
            System.out.println("An error occurred while showing all products in the cart: " + e.getMessage());
        }
    }
}