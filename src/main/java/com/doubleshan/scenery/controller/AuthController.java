package com.doubleshan.scenery.controller;

import com.doubleshan.scenery.common.api.ApiResponse;
import com.doubleshan.scenery.dto.AuthDtos;
import com.doubleshan.scenery.dto.AdminDtos;
import com.doubleshan.scenery.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<AuthDtos.RegisterResp> register(@RequestBody @Valid AuthDtos.RegisterReq req) {
        return ApiResponse.ok(authService.register(req));
    }

    @PostMapping("/login")
    public ApiResponse<AuthDtos.LoginResp> login(@RequestBody @Valid AuthDtos.LoginReq req) {
        return ApiResponse.ok(authService.login(req));
    }

    @PostMapping("/adminLogin")
    public ApiResponse<AdminDtos.AdminLoginResp> adminLogin(@RequestBody @Valid AdminDtos.AdminLoginReq req) {
        String token = authService.adminLogin(req.getUsername(), req.getPassword());
        AdminDtos.AdminLoginResp resp = new AdminDtos.AdminLoginResp();
        resp.setToken(token);
        return ApiResponse.ok(resp);
    }

    @PostMapping("/bindPhone")
    public ApiResponse<Void> bindPhone(@RequestBody @Valid AuthDtos.BindPhoneReq req, Authentication authentication) {
        Long userId = currentUserId(authentication);
        authService.bindPhone(userId, req.getPhone());
        return ApiResponse.ok(null);
    }

    private Long currentUserId(Authentication authentication) {
        // 简化：token subject 已经是 userId
        String name = authentication.getName();
        try {
            return Long.valueOf(name);
        } catch (Exception e) {
            return -1L;
        }
    }
}
