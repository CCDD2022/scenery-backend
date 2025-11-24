package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "poi")
public class POI {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(length = 128)
    private String name;
    @Column(length = 32)
    private String type; // scenic / hotel / restaurant
    private double latitude;
    private double longitude;
    private int heat; // 人气指标
    @Column(length = 1000)
    private String description;
    @Column(length = 1000)
    private String media; // 简化: 逗号分隔资源URL

    public POI() {
    }

    public POI(String name, String type, double lat, double lon) {
        this.name = name;
        this.type = type;
        this.latitude = lat;
        this.longitude = lon;
        this.heat = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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

    public String getDescription() {
        return description;
    }

    public String getMedia() {
        return media;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public void incHeat() {
        this.heat++;
    }
}
