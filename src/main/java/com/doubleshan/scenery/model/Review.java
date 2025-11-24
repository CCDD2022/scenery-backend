package com.doubleshan.scenery.model;

import java.time.Instant;
import java.util.UUID;

public class Review {
    private String id = UUID.randomUUID().toString();
    private String userId;
    private String poiId;
    private String checkinId;
    private int stars;
    private String content;
    private Instant createdAt = Instant.now();

    public Review() {
    }

    public Review(String userId, String poiId, String checkinId, int stars, String content) {
        this.userId = userId;
        this.poiId = poiId;
        this.checkinId = checkinId;
        this.stars = stars;
        this.content = content;
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

    public String getCheckinId() {
        return checkinId;
    }

    public int getStars() {
        return stars;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
