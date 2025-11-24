package com.doubleshan.scenery.model;

import java.util.UUID;

public class PointsRule {
    private String id = UUID.randomUUID().toString();
    private String code; // CHECKIN, REVIEW, LIKE
    private int points;
    private String description;

    public PointsRule() {
    }

    public PointsRule(String code, int points, String description) {
        this.code = code;
        this.points = points;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }
}
