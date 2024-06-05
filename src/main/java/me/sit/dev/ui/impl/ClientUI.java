package me.sit.dev.ui.impl;

import me.sit.dev.service.standalone.ServiceFactory;
import me.sit.dev.ui.BaseUI;

public class ClientUI extends BaseUI {

    public ClientUI(ServiceFactory serviceFactory) {
        super("Client UI", "This UI only shows the client's view.", serviceFactory);

    }

    @Override
    public void show() {
        System.out.println("Client UI");
    }
}
