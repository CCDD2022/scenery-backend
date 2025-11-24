package com.doubleshan.scenery.controller.visitor;

import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.SecurityContext;
import com.doubleshan.scenery.service.GiftService;
import com.doubleshan.scenery.vo.GiftRedeemVO;
import com.doubleshan.scenery.vo.GiftVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gifts")
public class GiftController {
    private final GiftService giftService;

    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @GetMapping
    public ApiResponse<List<GiftVO>> list() {
        return ApiResponse.ok(giftService.list().stream().map(GiftVO::from).toList());
    }

    @PostMapping("/{id}/redeem")
    public ApiResponse<GiftRedeemVO> redeem(@PathVariable String id) {
        String userId = SecurityContext.getUserId();
        return ApiResponse.ok(GiftRedeemVO.from(giftService.redeem(id, userId)));
    }

    @GetMapping("/my")
    public ApiResponse<List<GiftRedeemVO>> my() {
        String userId = SecurityContext.getUserId();
        return ApiResponse.ok(giftService.myRedeems(userId).stream().map(GiftRedeemVO::from).toList());
    }
}
