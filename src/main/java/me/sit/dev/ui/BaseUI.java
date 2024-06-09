package me.sit.dev.ui;

import me.sit.dev.service.ServiceFactory;
import me.sit.dev.service.IUserService;
import me.sit.dev.service.IRestaurantService;
import me.sit.dev.service.IProductService;
import me.sit.dev.service.IOrderService;
import me.sit.dev.service.ICartService;
import me.sit.dev.service.impl.CartService;

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

        this.userService = serviceFactory.getUserService();
        this.restaurantService = serviceFactory.getRestaurantService();
        this.productService = serviceFactory.getProductService();
        this.orderService = serviceFactory.getOrderService();
        this.cartService = serviceFactory.getCartService();

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