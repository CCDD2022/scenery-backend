package com.doubleshan.scenery.controller.common;

import com.doubleshan.scenery.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/external")
public class ExternalPlaceholderController {

    static class SmsReq {
        public String phone;
    }

    @PostMapping("/sms/send")
    public ApiResponse<Map<String, Object>> sms(@RequestBody SmsReq req) {
        return ApiResponse.ok(Map.of("status", "mock", "phone", req.phone, "code", "123456"));
    }

    static class IdReq {
        public String name;
        public String idCard;
    }

    @PostMapping("/id/verify")
    public ApiResponse<Map<String, Object>> idVerify(@RequestBody IdReq req) {
        return ApiResponse.ok(Map.of("passed", true, "score", 1.0, "name", req.name));
    }

    static class WxReq {
        public String code;
    }

    @PostMapping("/wechat/login")
    public ApiResponse<Map<String, Object>> wechatLogin(@RequestBody WxReq req) {
        return ApiResponse
                .ok(Map.of("openId", "mock-openid-" + req.code, "unionId", "mock-union", "nickname", "游客" + req.code));
    }
}