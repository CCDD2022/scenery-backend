package com.doubleshan.scenery.dto;

import lombok.Data;

public class SpotDtos {
    @Data
    public static class SpotResp {
        private Long id;
        private String name;
        private Double lat;
        private Double lng;
        private String description;
        private String image;
    }
}
