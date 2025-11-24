package com.doubleshan.scenery.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public class CheckInDtos {
    @Data
    public static class CreateCheckInReq {
        @NotNull
        private Long spotId;
        @NotBlank
        private String photoFileID;
        private String comment;
        @NotNull
        private Double lat;
        @NotNull
        private Double lng;
    }

    @Data
    public static class LikeReq {
        @NotNull
        private Long checkInId;
    }

    @Data
    public static class LikeResp {
        private Long checkInId;
        private Long likeCount;
    }
}
