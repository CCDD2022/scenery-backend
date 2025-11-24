package com.doubleshan.scenery.model;

public class Intro {
    private String title;
    private String bodyMd;
    private String mediaUrls; // 逗号分隔

    public Intro() {
    }

    public Intro(String title, String bodyMd, String mediaUrls) {
        this.title = title;
        this.bodyMd = bodyMd;
        this.mediaUrls = mediaUrls;
    }

    public String getTitle() {
        return title;
    }

    public String getBodyMd() {
        return bodyMd;
    }

    public String getMediaUrls() {
        return mediaUrls;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public void setBodyMd(String b) {
        this.bodyMd = b;
    }

    public void setMediaUrls(String m) {
        this.mediaUrls = m;
    }
}
