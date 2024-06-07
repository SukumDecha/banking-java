package me.sit.dev.main;

import me.sit.dev.repository.type.RepositoryType;
import me.sit.dev.service.standalone.ServiceFactory;
import me.sit.dev.ui.impl.ClientUI;
import me.sit.dev.ui.impl.RestaurantUI;

public class Application {

    public static void main(String[] args) {
        ServiceFactory serviceFactory = new ServiceFactory(RepositoryType.FILE);

        ClientUI clientUI = new ClientUI(serviceFactory);
        RestaurantUI restaurantUI = new RestaurantUI(serviceFactory);

        clientUI.show();
        restaurantUI.show();



    }
}
