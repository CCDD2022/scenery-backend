package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.Gift;

public class GiftVO {
    public String id;
    public String name;
    public int pointsCost;
    public int stock;
    public String supplierId;

    public static GiftVO from(Gift g) {
        GiftVO v = new GiftVO();
        v.id = g.getId();
        v.name = g.getName();
        v.pointsCost = g.getPointsCost();
        v.stock = g.getStock();
        v.supplierId = g.getSupplierId();
        return v;
    }
}
