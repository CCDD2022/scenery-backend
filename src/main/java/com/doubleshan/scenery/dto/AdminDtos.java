package com.doubleshan.scenery.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

public class AdminDtos {
    @Data
    public static class AdminLoginReq {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @Data
    public static class AdminLoginResp {
        private String token;
    }

    @Data
    public static class VerifyPrizeResp {
        private Long userId;
        private String userNickName;
        private String prizeInfo;
    }

    @Data
    public static class UserSummaryResp {
        private Long userId;
        private String username;
        private String nickName;
        private String phone;
        private Integer points;
        private Integer checkInCount;
    }
}
