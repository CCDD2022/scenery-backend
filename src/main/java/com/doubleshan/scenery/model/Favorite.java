package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "favorite")
public class Favorite {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(length = 64)
    private String userId;
    @Column(length = 64)
    private String poiId;

    public Favorite() {
    }

    public Favorite(String userId, String poiId) {
        this.userId = userId;
        this.poiId = poiId;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPoiId() {
        return poiId;
    }
}
