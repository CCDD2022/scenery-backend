package com.doubleshan.scenery.model;

import java.util.UUID;

public class MapAsset {
    private String id = UUID.randomUUID().toString();
    private String url;
    private String category; // image / video

    public MapAsset() {
    }

    public MapAsset(String url, String category) {
        this.url = url;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }
}
