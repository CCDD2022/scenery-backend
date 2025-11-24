package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.PointLedger;

import java.time.Instant;

public class PointLedgerVO {
    private String id;
    private String type;
    private int amount;
    private String reasonCode;
    private Instant createdAt;

    public static PointLedgerVO from(PointLedger pl) {
        if (pl == null)
            return null;
        PointLedgerVO vo = new PointLedgerVO();
        vo.id = pl.getId();
        vo.type = pl.getType();
        vo.amount = pl.getAmount();
        vo.reasonCode = pl.getReasonCode();
        vo.createdAt = pl.getCreatedAt();
        return vo;
    }

    public String getId() {
        return id;
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
