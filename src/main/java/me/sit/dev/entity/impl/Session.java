package me.sit.dev.entity.impl;

import me.sit.dev.entity.BaseEntity;
import me.sit.dev.entity.impl.user.User;

public class Session extends BaseEntity {
    private final User user;
    private final long loginTime;
    private long logoutTime;

    private Product selectingProduct;
    private String restaurantId;

    private static Session currentSession;

    public Session(User user) {
        super("session-" + user.getId());
        this.user = user;
        this.loginTime = System.currentTimeMillis();
    }

    public static void createSession(User user) {
        currentSession = new Session(user);
    }

    public static void destroySession() {
        currentSession = null;
    }

    public static Session getCurrentSession() {
        return currentSession;
    }

    public User getUser() {
        return user;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public long getLogoutTime() {
        return logoutTime;
    }

    public long getDuration() {
        return logoutTime - loginTime;
    }

    public Product getSelectingProduct() {
        return selectingProduct;
    }

    public String getRestaurantId() {
        return restaurantId;
    }


    public void setLogoutTime(long logoutTime) {
        this.logoutTime = logoutTime;
    }

    public void setSelectingProduct(Product product) {
        selectingProduct = product;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
