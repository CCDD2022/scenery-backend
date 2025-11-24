package com.doubleshan.scenery.controller.admin;

import com.doubleshan.scenery.model.GiftRedeem;
import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.RoleRequired;
import com.doubleshan.scenery.service.AdminGiftService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/gift-redeems")
@RoleRequired({ "ADMIN" })
public class AdminGiftRedeemController {
    private final AdminGiftService service;

    public AdminGiftRedeemController(AdminGiftService s) {
        this.service = s;
    }

    @GetMapping
    public ApiResponse<List<GiftRedeem>> list(@RequestParam(required = false) Boolean used) {
        return ApiResponse.ok(service.list(used));
    }

    @GetMapping("/stats")
    public ApiResponse<Map<String, Long>> stats() {
        return ApiResponse.ok(service.stats());
    }
}