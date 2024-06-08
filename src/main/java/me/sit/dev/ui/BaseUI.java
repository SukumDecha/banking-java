package me.sit.dev.ui;

import me.sit.dev.entity.impl.Product;
import me.sit.dev.entity.impl.Restaurant;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.service.ServiceFactory;
import me.sit.dev.service.*;
import me.sit.dev.service.impl.*;

public abstract class BaseUI {
    private final String uiName;
    private final String description;

    protected final IUserService userService;
    protected final IRestaurantService restaurantService;

    protected final IProductService productService;
    protected final IOrderService orderService;
    protected final ICartService cartService;

    public BaseUI(String uiName, String description, ServiceFactory serviceFactory) {
        this.uiName = uiName;
        this.description = description;

        this.userService = serviceFactory.createUserService();
        this.restaurantService = serviceFactory.createRestaurantService();

        this.productService = serviceFactory.createProductService();
        this.orderService = serviceFactory.createOrderService(productService);
        this.cartService = new CartService();

        System.out.println("Services registered for UI: " + uiName);
    }

    public abstract void show();

    public String getUiName() {
        return uiName;
    }

    public String getDescription() {
        return description;
    }

}