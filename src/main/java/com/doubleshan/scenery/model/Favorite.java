package com.doubleshan.scenery.model;

import java.util.UUID;

public class Favorite {
    private String id = UUID.randomUUID().toString();
    private String userId;
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
