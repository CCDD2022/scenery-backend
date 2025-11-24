package com.doubleshan.scenery.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Checkin {
    private String id = UUID.randomUUID().toString();
    private String userId;
    private String poiId;
    private double latitude;
    private double longitude;
    private String photo;
    private Instant createdAt = Instant.now();
    private Set<String> likes = new HashSet<>();

    public Checkin() {
    }

    public Checkin(String userId, String poiId, double lat, double lon, String photo) {
        this.userId = userId;
        this.poiId = poiId;
        this.latitude = lat;
        this.longitude = lon;
        this.photo = photo;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPhoto() {
        return photo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public int getLikeCount() {
        return likes.size();
    }

    public void like(String userId) {
        likes.add(userId);
    }
}
