package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.GiftRedeem;
import java.time.Instant;

public class GiftRedeemVO {
    public String id;
    public String giftId;
    public String userId;
    public String qrcode;
    public boolean used;
    public Instant usedAt;
    public String merchantId;

    public static GiftRedeemVO from(GiftRedeem r) {
        GiftRedeemVO v = new GiftRedeemVO();
        v.id = r.getId();
        v.giftId = r.getGiftId();
        v.userId = r.getUserId();
        v.qrcode = r.getQrcode();
        v.used = r.isUsed();
        v.usedAt = r.getUsedAt();
        v.merchantId = r.getMerchantId();
        return v;
    }
}
