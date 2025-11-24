package com.doubleshan.scenery.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

public class UserDtos {
    @Data
    public static class UpdateAvatarReq {
        @NotBlank
        private String avatarUrl;
    }

    @Data
    public static class ProfileResp {
        private Long userId;
        private String nickName;
        private String avatar;
        private String phone;
        private Integer totalPoints;
        private Integer checkInCount;
    }
}
