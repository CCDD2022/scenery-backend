package com.doubleshan.scenery.model;

import java.util.UUID;

public class Merchant {
    private String id = UUID.randomUUID().toString();
    private String name;
    private double latitude;
    private double longitude;
    private int heat;
    private String address;
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
