package me.sit.dev.entity.impl;

import me.sit.dev.entity.BaseEntity;
import me.sit.dev.entity.impl.user.User;
import me.sit.dev.service.standalone.UtilityService;

public class Session extends BaseEntity {
    private final User user;
    private final long loginTime;
    private long logoutTime;
    private static Session currentSession;

    public Session(User user) {
        super(UtilityService.generateId("session-"));
        this.user = user;
        this.loginTime = System.currentTimeMillis();
    }

    public static Session createSession(User user) {
        return currentSession = new Session(user);
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

    public void setLogoutTime(long logoutTime) {
        this.logoutTime = logoutTime;
    }

}
