package me.sit.dev.entity;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {
    private final String id;

    public BaseEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
