package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "supplier")
public class Supplier {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(length = 128)
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
