package me.sit.dev.ui.impl;

import me.sit.dev.entity.impl.order.Order;
import me.sit.dev.repository.impl.restaurant.RestaurantFileRepo;
import me.sit.dev.service.impl.RestaurantService;
import me.sit.dev.service.ServiceFactory;
import me.sit.dev.ui.BaseUI;

import java.util.Scanner;

public class ClientUI extends BaseUI {
    private String programPrompt = """
                                
            -------------- MAIN MENU --------------
                        1. Create Restaurant          
                        2. Order Food
                        3. Logout
            ---------------------------------------       
            """;
    private String orderUIPrompt = """
                                
            -------------- Order UI --------------
                       1. order food     
                       2. search food   
                         3. see cart     
                    4. Back to main menu
            ---------------------------------------       
            """;
    private String selectMenuPrompt = """
                    
            -------------- select food menu --------------
                            1. select food      
                           2. select amount  
                          3. add to cart UI      
                            4. checkout ui
            ----------------------------------------------       
            """;

    Scanner sc = new Scanner(System.in);
    public ClientUI(ServiceFactory serviceFactory) {
        super("Client UI", "This UI only shows the client's view.", serviceFactory);
    }

    @Override
    public void show() {
        System.out.println("Client UI");
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
                    createRestaurant();
                    System.out.println("Create Restaurant");
                    continue;
                case 2:
                    orderUI();
                    System.out.println("Order Food");
                    continue;
                case 3:
                    System.out.println("Logout success");
                    program_status = false;
                    break;
            }
        }
    }
    public void createRestaurant() {
        RestaurantService restaurantService = new RestaurantService(new RestaurantFileRepo());
        System.out.println("Enter ownerId : ");
        String ownerId = sc.next();
        System.out.println("Enter restaurant name : ");
        String restaurantName = sc.next();
        restaurantService.addRestaurant(ownerId, restaurantName);
        System.out.println("success create Restaurant");
    }
    public void orderUI() {
        productService.showAllProducts();
        System.out.println(orderUIPrompt);
        Scanner scOrder = new Scanner(System.in);
        System.out.print("Select your order : ");
        while (!scOrder.hasNext("[1|2|3|4]")) {
            System.out.print("please try again [select 1,2,3,4] : ");
            scOrder.next();
        }
        boolean orderstatus = true;
        int orderCount = 1;
        int orderSelected;
        while (orderstatus) {
            if (orderCount != 1) {
                System.out.println(orderUIPrompt);
                System.out.print("Select your next order : ");
                while (!scOrder.hasNext("[1|2|3|4]")) {
                    System.out.print("please try again [select 1,2,3] : ");
                    scOrder.next();
                }
            }
            orderCount++;
            orderSelected = scOrder.nextInt();
            switch (orderSelected) {
                case 1:
                    System.out.println("order food method");
                    orderfood();
                    continue;
                case 2:
                    System.out.println("search food");
                    search_food();
                    continue;
                case 3:
                    System.out.println("in my cart");
                    inMyCart();
                    continue;
                case 4:
                    System.out.println("Back to main menu");
                    orderstatus = false;
                    break;
            }
        }
    }

    public void orderfood() {
        boolean statusSelectFood = true;
        while (statusSelectFood) {
            System.out.println("ui order food");
            Scanner scFood = new Scanner(System.in);
            System.out.print("Select menu : ");
            while (!productService.findAll().equals(scFood.hasNext())) {
                System.out.println("you can enter 0 to end this process");
                System.out.print("please try again (may be your text error): ");
                scFood.next();
                if (scFood.hasNext("0")){
                    statusSelectFood=false;
                }
            }

            System.out.println("Show all detail of that food");
            orderService.showOrderDetails(new Order(user),false);
            addToCart();
        }
    }

    public void search_food() {
        System.out.println("search : ");
        String search=sc.next();
        productService.findByName(search);
        orderfood();
    }

    public void inMyCart() {
        cartService.showCartDetails(user);
        Scanner scIncart = new Scanner(System.in);
        boolean statusCart = true;
        while (statusCart) {
            System.out.print("add others or remove menu in cart or check bill or go back: ");
            while (!scIncart.hasNext("\\b(add|remove|bill)\\b")) {
                System.out.print("please try again : ");
                scIncart.next();
            }
            String newOrder = scIncart.next();
            switch (newOrder) {
                case "add":
                    orderfood();
                    statusCart = false;
                    continue;
                case "remove":
                    removeFromCart();
                    System.out.println("removeeee");
                    continue;
                case "bill":
                    System.out.println("billlllll");
                    orderSummary();
                    statusCart = false;
//                    statusSelectFood=false;
                    continue;
                case "back":
                    System.out.println("go back");
                    statusCart = false;
                    break;
            }
        }
    }



    public void addToCart() {
        System.out.println("Did you want to add to cart");
        Scanner scAddToCart = new Scanner(System.in);
        System.out.print("Yes or No : ");
        while (!scAddToCart.hasNext("(?i)Yes|(?i)No")) {
            System.out.print("please try again (input Yes or No): ");
            scAddToCart.next();
        }

        String addToCartSelected = scAddToCart.next();
        if (addToCartSelected.equalsIgnoreCase("Yes")) {
            Scanner scAmout = new Scanner(System.in);
            System.out.print("Select amout : ");
            while (!scAmout.hasNextInt()) {
                System.out.print("please try again (only input number): ");
                scAmout.next();
            }
            System.out.println("add more or enough");
            System.out.println("success added");
        }
        if (addToCartSelected.equalsIgnoreCase("No")) {
            System.out.println("cancel this order");
        }
    }

    public void removeFromCart() {
        System.out.println("What did you want to remove from cart");
        String remove=sc.next();

        try{
            product=productService.findByName(remove);
        }catch (Exception e){
            System.out.println("not have this food");
        }

        while (!cartService.existsInCart(user,product)){
            System.out.println("please try again");
            remove=sc.next();
            try{
                product=productService.findByName(remove);
            }catch (Exception e){
                System.out.println("not have this food");
            }
        }

        System.out.print("Confirm remove Yes or No : ");
        sc.next();
        while (!sc.hasNext("(?i)Yes|(?i)No")) {
            System.out.print("please try again (input Yes or No): ");
            sc.next();
        }
        String addToCartSelected = sc.next();
        if (addToCartSelected.equalsIgnoreCase("Yes")) {
            cartService.removeFromCart(user,product);
            System.out.println("success remove");
        }
        if (addToCartSelected.equalsIgnoreCase("No")) {
            System.out.println("cancel remove process");
        }
    }

    public void orderSummary() {
        System.out.println("Sum");
    }
}
