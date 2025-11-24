package com.doubleshan.scenery.controller.admin;

import com.doubleshan.scenery.model.Checkin;
import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.RoleRequired;
import com.doubleshan.scenery.service.AdminCheckinService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/checkins")
@RoleRequired({ "ADMIN" })
public class AdminCheckinManageController {
    private final AdminCheckinService service;

    public AdminCheckinManageController(AdminCheckinService s) {
        this.service = s;
    }

    @GetMapping
    public ApiResponse<List<Checkin>> list(@RequestParam(required = false) String poiId,
            @RequestParam(required = false) Long start,
            @RequestParam(required = false) Long end,
            @RequestParam(required = false) String userId) {
        Instant s = start == null ? null : Instant.ofEpochMilli(start);
        Instant e = end == null ? null : Instant.ofEpochMilli(end);
        return ApiResponse.ok(service.list(poiId, s, e, userId));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/stats/poi")
    public ApiResponse<Map<String, Long>> statsByPoi() {
        return ApiResponse.ok(service.statsByPoi());
    }
}