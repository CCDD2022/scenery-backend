package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.Merchant;

public class MerchantVO {
    public String id;
    public String name;
    public double latitude;
    public double longitude;
    public int heat;
    public String address;
    public String phone;

    public static MerchantVO from(Merchant m) {
        MerchantVO v = new MerchantVO();
        v.id = m.getId();
        v.name = m.getName();
        v.latitude = m.getLatitude();
        v.longitude = m.getLongitude();
        v.heat = m.getHeat();
        v.address = m.getAddress();
        v.phone = m.getPhone();
        return v;
    }
}
