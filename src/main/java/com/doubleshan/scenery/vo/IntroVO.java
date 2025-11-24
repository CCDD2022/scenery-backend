package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.Intro;

public class IntroVO {
    public String title;
    public String bodyMd;
    public String mediaUrls;

    public static IntroVO from(Intro i) {
        IntroVO v = new IntroVO();
        v.title = i.getTitle();
        v.bodyMd = i.getBodyMd();
        v.mediaUrls = i.getMediaUrls();
        return v;
    }
}
