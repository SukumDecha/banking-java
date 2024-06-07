package me.sit.dev.ui.impl;

import me.sit.dev.service.standalone.ServiceFactory;
import me.sit.dev.ui.BaseUI;

public class RestaurantUI extends BaseUI {


    public RestaurantUI(ServiceFactory serviceFactory) {
        super("Restaurant UI", "This UI only shows the restaurant's view.", serviceFactory);
    }

    @Override
    public void show() {
        System.out.println("Restaurant UI");
    }
}
