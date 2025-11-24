package com.doubleshan.scenery.controller.admin;

import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.RoleRequired;
import com.doubleshan.scenery.service.StatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/stats")
@RoleRequired({ "ADMIN" })
public class AdminStatsController {
    private final StatsService statsService;

    public AdminStatsController(StatsService s) {
        this.statsService = s;
    }

    @GetMapping("/global")
    public ApiResponse<Map<String, Object>> global() {
        return ApiResponse.ok(statsService.globalStats());
    }
}