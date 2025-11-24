package com.doubleshan.scenery.model;

import java.time.Instant;
import java.util.UUID;

public class PointLedger {
    private String id = UUID.randomUUID().toString();
    private String userId;
    private String type; // Earn / Spend
    private int amount;
    private String reasonCode; // e.g. CHECKIN, REDEEM
    private Instant createdAt = Instant.now();

    public PointLedger() {
    }

    public PointLedger(String userId, String type, int amount, String reason) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.reasonCode = reason;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
