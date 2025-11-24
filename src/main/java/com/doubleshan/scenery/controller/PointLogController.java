package com.doubleshan.scenery.controller;

import com.doubleshan.scenery.common.api.ApiResponse;
import com.doubleshan.scenery.model.PointLog;
import com.doubleshan.scenery.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointLogController {
    private final PointService pointService;

    @GetMapping("/list")
    public ApiResponse<Page<PointLog>> list(@RequestParam(defaultValue = "0") int page, Authentication a) {
        return ApiResponse.ok(pointService.list(userId(a), page));
    }

    private Long userId(Authentication a) {
        try {
            return Long.valueOf(a.getName());
        } catch (Exception e) {
            return -1L;
        }
    }
}
