package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "gift")
public class Gift {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(length = 128)
    private String name;
    private int pointsCost;
    private int stock;
    @Column(length = 64)
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
