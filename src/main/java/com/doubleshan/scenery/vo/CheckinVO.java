package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.Checkin;

import java.time.Instant;

public class CheckinVO {
    public String id;
    public String userId;
    public String poiId;
    public String photo;
    public Instant createdAt;
    public int likeCount;

    public static CheckinVO from(Checkin c) {
        CheckinVO v = new CheckinVO();
        v.id = c.getId();
        v.userId = c.getUserId();
        v.poiId = c.getPoiId();
        v.photo = c.getPhoto();
        v.createdAt = c.getCreatedAt();
        v.likeCount = c.getLikeCount();
        return v;
    }
}
