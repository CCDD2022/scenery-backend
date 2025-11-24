package com.doubleshan.scenery.controller.merchant;

import com.doubleshan.scenery.model.GiftRedeem;
import com.doubleshan.scenery.model.Merchant;
import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.JwtUtil;
import com.doubleshan.scenery.security.RoleRequired;
import com.doubleshan.scenery.security.SecurityContext;
import com.doubleshan.scenery.service.GiftService;
import com.doubleshan.scenery.service.MerchantService;
import com.doubleshan.scenery.vo.GiftRedeemVO;
import com.doubleshan.scenery.vo.MerchantVO;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/merchant")
public class MerchantController {
    private final MerchantService merchantService;
    private final GiftService giftService;

    public MerchantController(MerchantService merchantService, GiftService giftService) {
        this.merchantService = merchantService;
        this.giftService = giftService;
    }

    static class LoginReq {
        public String merchant_id;
        public String username;
        public String password;
    }

    @PostMapping("/login")
    public ApiResponse<Object> login(@RequestBody LoginReq req) {
        Merchant m = merchantService.get(req.merchant_id);
        String token = JwtUtil.generateToken(m.getId(), "MERCHANT");
        return ApiResponse.ok(java.util.Map.of("token", token, "merchant", MerchantVO.from(m)));
    }

    @PostMapping("/gifts/redeems/{id}/verify")
    @RoleRequired({ "MERCHANT" })
    public ApiResponse<GiftRedeemVO> verify(@PathVariable String id) {
        String merchantId = SecurityContext.getUserId();
        return ApiResponse.ok(GiftRedeemVO.from(giftService.verify(id, merchantId)));
    }

    @GetMapping("/verify/logs")
    @RoleRequired({ "MERCHANT" })
    public ApiResponse<List<GiftRedeemVO>> logs() {
        String merchantId = SecurityContext.getUserId();
        return ApiResponse.ok(giftService.allRedeems().stream().filter(GiftRedeem::isUsed)
                .filter(r -> merchantId.equals(r.getMerchantId())).map(GiftRedeemVO::from)
                .collect(Collectors.toList()));
    }

    @GetMapping("/verify/stats")
    @RoleRequired({ "MERCHANT" })
    public ApiResponse<Map<String, Object>> stats(@RequestParam(defaultValue = "day") String period) {
        String merchantId = SecurityContext.getUserId();
        Instant now = Instant.now();
        Instant start;
        switch (period) {
            case "week":
                start = now.minusSeconds(7 * 86400L);
                break;
            case "month":
                start = now.minusSeconds(30 * 86400L);
                break;
            case "day":
            default:
                start = now.minusSeconds(86400L);
                break;
        }
        long count = giftService.allRedeems().stream().filter(GiftRedeem::isUsed)
                .filter(r -> merchantId.equals(r.getMerchantId()))
                .filter(r -> r.getUsedAt() != null && r.getUsedAt().isAfter(start)).count();
        Map<String, Object> m = Map.of("period", period, "count", count, "from",
                DateTimeFormatter.ISO_INSTANT.format(start), "to", DateTimeFormatter.ISO_INSTANT.format(now));
        return ApiResponse.ok(m);
    }
}
