package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.Review;
import java.time.Instant;

public class ReviewVO {
    public String id;
    public String userId;
    public String poiId;
    public String checkinId;
    public int stars;
    public String content;
    public Instant createdAt;

    public static ReviewVO from(Review r) {
        ReviewVO v = new ReviewVO();
        v.id = r.getId();
        v.userId = r.getUserId();
        v.poiId = r.getPoiId();
        v.checkinId = r.getCheckinId();
        v.stars = r.getStars();
        v.content = r.getContent();
        v.createdAt = r.getCreatedAt();
        return v;
    }
}
