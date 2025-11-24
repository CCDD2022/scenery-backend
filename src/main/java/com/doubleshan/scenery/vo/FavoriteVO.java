package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.Favorite;

public class FavoriteVO {
    private String id;
    private String poiId;

    public static FavoriteVO from(Favorite f) {
        if (f == null)
            return null;
        FavoriteVO vo = new FavoriteVO();
        vo.id = f.getId();
        vo.poiId = f.getPoiId();
        return vo;
    }

    public String getId() {
        return id;
    }

    public String getPoiId() {
        return poiId;
    }
}
