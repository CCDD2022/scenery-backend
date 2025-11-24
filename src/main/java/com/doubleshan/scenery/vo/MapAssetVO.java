package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.MapAsset;

public class MapAssetVO {
    public String id;
    public String url;
    public String category;

    public static MapAssetVO from(MapAsset m) {
        MapAssetVO v = new MapAssetVO();
        v.id = m.getId();
        v.url = m.getUrl();
        v.category = m.getCategory();
        return v;
    }
}
