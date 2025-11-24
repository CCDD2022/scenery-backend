package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.Hotline;

public class HotlineVO {
    public String number;

    public static HotlineVO from(Hotline h) {
        HotlineVO v = new HotlineVO();
        v.number = h.getNumber();
        return v;
    }
}
