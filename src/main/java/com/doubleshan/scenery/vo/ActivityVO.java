package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.ActivityDoc;

public class ActivityVO {
    public String bodyMd;

    public static ActivityVO from(ActivityDoc a) {
        ActivityVO v = new ActivityVO();
        v.bodyMd = a.getBodyMd();
        return v;
    }
}
