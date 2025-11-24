package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "point_ledger")
public class PointLedger {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(length = 64)
    private String userId;
    @Column(length = 16)
    private String type; // Earn / Spend
    private int amount;
    @Column(length = 32)
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
