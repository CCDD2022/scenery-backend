package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "points_rule")
public class PointsRule {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(length = 32, unique = true)
    private String code; // CHECKIN, REVIEW, LIKE
    private int points;
    @Column(length = 256)
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
