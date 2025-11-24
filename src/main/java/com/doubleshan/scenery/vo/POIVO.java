package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.POI;

public class POIVO {
    public String id;
    public String name;
    public String type;
    public double latitude;
    public double longitude;
    public int heat;
    public String description;
    public String media;
    public Double distanceMeters; // 与请求坐标的距离, 可为空
    public String categoryName; // 中文分类名: 景点/住宿/饭店

    public static POIVO from(POI p) {
        POIVO v = new POIVO();
        v.id = p.getId();
        v.name = p.getName();
        v.type = p.getType();
        v.latitude = p.getLatitude();
        v.longitude = p.getLongitude();
        v.heat = p.getHeat();
        v.description = p.getDescription();
        v.media = p.getMedia();
        v.categoryName = mapCategory(p.getType());
        return v;
    }

    public static POIVO fromWithDistance(POI p, Double distance) {
        POIVO v = from(p);
        v.distanceMeters = distance;
        return v;
    }

    private static String mapCategory(String type) {
        if (type == null)
            return null;
        if ("scenic".equals(type))
            return "景点";
        if ("hotel".equals(type))
            return "住宿";
        if ("restaurant".equals(type))
            return "饭店";
        return type;
    }
}
