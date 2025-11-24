package com.doubleshan.scenery.model;

import java.util.UUID;

public class Gift {
    private String id = UUID.randomUUID().toString();
    private String name;
    private int pointsCost;
    private int stock;
    private String supplierId;

    public Gift() {
    }

    public Gift(String name, int pointsCost, int stock) {
        this.name = name;
        this.pointsCost = pointsCost;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPointsCost() {
        return pointsCost;
    }

    public int getStock() {
        return stock;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setPointsCost(int c) {
        this.pointsCost = c;
    }

    public void setStock(int s) {
        this.stock = s;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public void decStock() {
        this.stock--;
    }
}
