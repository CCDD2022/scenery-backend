package com.doubleshan.scenery.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

public class AuthDtos {
    @Data
    public static class RegisterReq {
        @NotBlank
        private String username;
        @NotBlank
        private String password; // 前端已MD5，这里仍然当作原始再Bcrypt
        @NotBlank
        private String nickName;
    }

    @Data
    public static class LoginReq {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @Data
    public static class BindPhoneReq {
        @NotBlank
        private String phone;
        @NotBlank
        private String code; // 简化不做校验逻辑
    }

    @Data
    public static class LoginResp {
        private Long userId;
        private String token;
        private String nickName;
        private String avatar;
    }

    @Data
    public static class RegisterResp {
        private Long userId;
        private String token;
    }
}
