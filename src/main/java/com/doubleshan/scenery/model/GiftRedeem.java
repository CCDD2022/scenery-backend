package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "gift_redeem")
public class GiftRedeem {
    @Id
    private String id = UUID.randomUUID().toString();
    @Column(length = 64)
    private String giftId;
    @Column(length = 64)
    private String userId;
    @Column(length = 128, unique = true)
    private String qrcode = UUID.randomUUID().toString();
    private boolean used;
    private Instant usedAt;
    @Column(length = 64)
    private String merchantId;

    public GiftRedeem() {
    }

    public GiftRedeem(String giftId, String userId) {
        this.giftId = giftId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getGiftId() {
        return giftId;
    }

    public String getUserId() {
        return userId;
    }

    public String getQrcode() {
        return qrcode;
    }

    public boolean isUsed() {
        return used;
    }

    public Instant getUsedAt() {
        return usedAt;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void verify(String merchantId) {
        this.used = true;
        this.usedAt = Instant.now();
        this.merchantId = merchantId;
    }
}
