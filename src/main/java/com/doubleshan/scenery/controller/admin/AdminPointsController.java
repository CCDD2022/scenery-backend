package com.doubleshan.scenery.controller.admin;

import com.doubleshan.scenery.model.PointLedger;
import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.RoleRequired;
import com.doubleshan.scenery.service.AdminPointsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/points-ledgers")
@RoleRequired({ "ADMIN" })
public class AdminPointsController {
    private final AdminPointsService service;

    public AdminPointsController(AdminPointsService s) {
        this.service = s;
    }

    @GetMapping
    public ApiResponse<List<PointLedger>> list(@RequestParam(required = false) String type,
            @RequestParam(required = false) String userId) {
        return ApiResponse.ok(service.list(type, userId));
    }

    @GetMapping("/stats")
    public ApiResponse<Map<String, Integer>> stats() {
        return ApiResponse.ok(service.stats());
    }
}