package me.sit.dev.ui;

import me.sit.dev.repository.impl.restaurant.RestaurantFileRepo;
import me.sit.dev.service.impl.OrderService;
import me.sit.dev.service.impl.ProductService;
import me.sit.dev.service.impl.RestaurantService;
import me.sit.dev.service.impl.UserService;

import java.util.Scanner;

public class TextBasedGui {
    private static String Login_prompt = """
                    
            -------------- LOGIN MENU --------------
                           1. Login
                          2. register         
                      3. out from program
            ----------------------------------------       
            """;
    private static String Program_prompt = """
                                
            -------------- MAIN MENU --------------
                        1. Create User
                     2. Create Restaurant          
                        3. Order Food
                          4. Logout
            ---------------------------------------       
            """;
    private static String OrderUI_prompt = """
                                
            -------------- Order UI --------------
                       1. order food     
                       2. search food   
                         3. see cart     
                    4. Back to main menu
            ---------------------------------------       
            """;
    private static String SelectMenu_prompt = """
                    
                    -------------- select food menu --------------
                                   1. select food      
                                  2. select amount  
                                  3. add to cart UI      
                                   4. checkout ui
                    ----------------------------------------------       
            """;
    private UserService userService;

    private static boolean statusSelectFood = true;
    Scanner sc = new Scanner(System.in);

    public TextBasedGui(UserService userService) {
        this.userService = userService;
        System.out.println(Login_prompt);
        System.out.println("please Enter 1/2/3");
        while (!sc.hasNext("[1|2|3]")) {
            System.out.println("please try again");
            sc.next();
        }

        int loginSelected = sc.nextInt();
        boolean login_status = true;
        while (login_status) {
            switch (loginSelected) {
                case 1:
                    System.out.println("in login method");
                    login(userService);
                    System.out.println("Sucess login");
                    clientProgram();
                    login_status = false;
                    continue;
                case 2:
                    System.out.println("in register method");
                    register(userService);
                    System.out.println("You have an account now. \nLes's login\n");
                    loginSelected = 1;
                    continue;
                case 3:
                    login_status = false;
                    break;
            }
        }
    }

    public static void UserInterface_Restaurant_Side() {
        System.out.println(Login_prompt);
        System.out.println("select your program");
        Scanner scLogin = new Scanner(System.in);
        while (!scLogin.hasNext("[1|2|3]")) {
            System.out.println("Please try again");
            scLogin.next();
        }
        int loginSelected = scLogin.nextInt();

    }

    public static void register(UserService userService) {
        System.out.println("Enter your Name");
        Scanner sc = new Scanner(System.in);
        String name = sc.next();
        System.out.println("Enter your Email");
        String email = sc.next();
        System.out.println("Enter your Password");
        String password = sc.next();
        System.out.println("Are you admin");
        while (!sc.hasNext("(?i)Yes|(?i)No")) {
            System.out.println("please enter yes or no");
            sc.next();
        }
        String isadmin = sc.next();
        boolean isAdmin = false;
        if (isadmin.equals("yes")) {
            isAdmin = true;
        }
        userService.register(name, email, password, isAdmin);
    }

    public void login(UserService userService) {
        System.out.print("Enter your email : ");
        Scanner sc = new Scanner(System.in);
        String email = sc.next();
        System.out.print("Enter your password : ");
        String password = sc.next();
        if (userService.login(email, password)) {
            System.out.println("bfgvdchsjkal;");
        }

//        boolean checkpassword=username.equals("a")&&password.equals("a");
//        while (checkpassword==false){
//            System.out.println("Something worng!! Please try again");
//            System.out.println("Enter your username : ");
//            username=sc .next();
//            System.out.println("Enter your password : ");
//            password=sc .next();
//            checkpassword=username.equals("a")&&password.equals("a");
//        }
//        if (checkpassword){
//            System.out.println("success login");
//            System.out.println("Start main menu Programm");
//        }
    }

    public void clientProgram() {
        System.out.println(Program_prompt);
        System.out.print("Choose your program : ");
        Scanner sc = new Scanner(System.in);
        while (!sc.hasNext("[1|2|3|4]")) {
            System.out.print("please try again [select 1,2,3,4] : ");
            sc.next();
        }
        boolean program_status = true;
        int count = 1;
        int programSelected;
        while (program_status) {
            if (count != 1) {
                System.out.println(Program_prompt);
                System.out.print("Choose next program : ");
                while (!sc.hasNext("[1|2|3|4]")) {
                    System.out.print("please try again [select 1,2,3,4] : ");
                    sc.next();
                }
            }
            programSelected = sc.nextInt();
            count++;
            switch (programSelected) {
                case 1:
                    createUser();
                    System.out.println("Create User");
                    continue;
                case 2:
                    createRestaurant();
                    System.out.println("Create Restaurant");
                    continue;
                case 3:
                    orderUI();
                    System.out.println("Order Food");
                    continue;
                case 4:
                    System.out.println("Logout success");
                    program_status = false;
                    break;
            }
        }
    }

    public static void createUser() {
        System.out.println("success create user");
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

    public static void orderUI() {
        System.out.println("show all food in program");
        System.out.println(OrderUI_prompt);
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
                System.out.println(OrderUI_prompt);
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
                    addToCart();
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

    public static void orderfood() {
        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();
        statusSelectFood = true;
        while (statusSelectFood) {
            System.out.println("\n1a\n2b\n3c");
            System.out.println("ui order food");
            Scanner scFood = new Scanner(System.in);
            System.out.print("Select menu : ");
            while (!productService.findAll().equals(scFood.hasNext())) {
//                System.out.println("you can enter 0 to end this process");
                System.out.print("please try again (may be your text error): ");
                scFood.next();
//                if (scFood.hasNext("0")){}
            }

            System.out.println("Show all detail of that food");
//            orderService.showOrderDetails(new Order());
            addToCart();
        }
    }

    public static void search_food() {
        System.out.println("This is your food ");
        System.out.println("I can find it");
    }

    public static void inMyCart() {
        showAllInCart();
        Scanner scIncart = new Scanner(System.in);
        boolean statusCart = true;
        while (statusCart) {
            System.out.print("add others or remove some menu in cart or check bill or go back: ");
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
                    System.out.println("removeeee");
                    removeFromCart();
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

    public static void showAllInCart() {
        System.out.println("Show every thing in cart");
    }

    public static void addToCart() {
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
            statusSelectFood = false;
        }
    }

    public static void removeFromCart() {
        System.out.println("Did you want to remove to cart");
        Scanner scRemoveFromCart = new Scanner(System.in);
        System.out.print("Yes or No : ");
        while (!scRemoveFromCart.hasNext("(?i)Yes|(?i)No")) {
            System.out.print("please try again (input Yes or No): ");
            scRemoveFromCart.next();
        }
        String addToCartSelected = scRemoveFromCart.next();
        if (addToCartSelected.equalsIgnoreCase("Yes")) {
            System.out.println("success remove");
        }
        if (addToCartSelected.equalsIgnoreCase("No")) {
            System.out.println("cancel this order");
            statusSelectFood = false;
        }
    }

    public static void orderSummary() {
        System.out.println("Sum");
    }

}
