package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.ActivityDoc;
import com.doubleshan.scenery.model.Hotline;
import com.doubleshan.scenery.model.Intro;
import org.springframework.stereotype.Service;

@Service
public class ContentService {
    private Intro intro = new Intro("双山引领区", "初始简介", "https://example.com/intro.jpg");
    private ActivityDoc activityDoc = new ActivityDoc("活动说明初始化内容");
    private Hotline hotline = new Hotline("400-000-0000");

    public Intro getIntro() {
        return intro;
    }

    public ActivityDoc getActivity() {
        return activityDoc;
    }

    public Hotline getHotline() {
        return hotline;
    }

    public Intro updateIntro(String title, String body, String media) {
        if (title != null)
            intro.setTitle(title);
        if (body != null)
            intro.setBodyMd(body);
        if (media != null)
            intro.setMediaUrls(media);
        return intro;
    }

    public ActivityDoc updateActivity(String body) {
        if (body != null)
            activityDoc.setBodyMd(body);
        return activityDoc;
    }

    public Hotline updateHotline(String number) {
        if (number != null)
            hotline.setNumber(number);
        return hotline;
    }
}
