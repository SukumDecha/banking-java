package me.sit.dev.ui.impl;

import me.sit.dev.service.standalone.ServiceFactory;
import me.sit.dev.ui.BaseUI;

import java.util.Scanner;

public class LoginUI extends BaseUI {

    private final ClientUI clientUI;
    private final RestaurantUI restaurantUI;

    public LoginUI(ClientUI clientUI, RestaurantUI restaurantUI, ServiceFactory serviceFactory) {
        super("Login UI", "This UI only shows the login view.", serviceFactory);

        this.clientUI = clientUI;
        this.restaurantUI = restaurantUI;
    }

    @Override
    public void show() {
        System.out.println("Login UI");
        boolean isClient;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Are you a client? (y/n)");
        String answer = scanner.nextLine();
        isClient = answer.equals("y");

        if (isClient) {
            clientUI.show();
        } else {
            restaurantUI.show();
        }
    }
}
