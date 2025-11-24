package com.doubleshan.scenery.model;

import java.util.UUID;

public class Supplier {
    private String id = UUID.randomUUID().toString();
    private String name;

    public Supplier() {
    }

    public Supplier(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        this.name = n;
    }
}
