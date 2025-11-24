package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "merchant")
public class Merchant {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(length = 128)
    private String name;
    private double latitude;
    private double longitude;
    private int heat;
    @Column(length = 256)
    private String address;
    @Column(length = 32)
    private String phone;

    public Merchant() {
    }

    public Merchant(String name, double lat, double lon) {
        this.name = name;
        this.latitude = lat;
        this.longitude = lon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getHeat() {
        return heat;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setAddress(String a) {
        this.address = a;
    }

    public void setPhone(String p) {
        this.phone = p;
    }

    public void incHeat() {
        this.heat++;
    }
}
