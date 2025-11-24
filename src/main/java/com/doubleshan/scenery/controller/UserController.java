package com.doubleshan.scenery.controller;

import com.doubleshan.scenery.common.api.ApiResponse;
import com.doubleshan.scenery.dto.UserDtos;
import com.doubleshan.scenery.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ApiResponse<UserDtos.ProfileResp> profile(Authentication authentication) {
        return ApiResponse.ok(userService.profile(userId(authentication)));
    }

    @PostMapping("/avatar")
    public ApiResponse<Void> updateAvatar(@RequestBody @Valid UserDtos.UpdateAvatarReq req,
            Authentication authentication) {
        userService.updateAvatar(userId(authentication), req.getAvatarUrl());
        return ApiResponse.ok(null);
    }

    private Long userId(Authentication a) {
        try {
            return Long.valueOf(a.getName());
        } catch (Exception e) {
            return -1L;
        }
    }
}
