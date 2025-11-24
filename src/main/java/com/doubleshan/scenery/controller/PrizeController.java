package com.doubleshan.scenery.controller;

import com.doubleshan.scenery.common.api.ApiResponse;
import com.doubleshan.scenery.dto.PrizeDtos;
import com.doubleshan.scenery.model.Prize;
import com.doubleshan.scenery.service.PrizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prize")
@RequiredArgsConstructor
public class PrizeController {
    private final PrizeService prizeService;

    @GetMapping("/list")
    public ApiResponse<List<Prize>> list() {
        return ApiResponse.ok(prizeService.list());
    }

    @PostMapping("/exchange")
    public ApiResponse<PrizeDtos.ExchangeResp> exchange(@RequestBody @Valid PrizeDtos.ExchangeReq req,
            Authentication a) {
        return ApiResponse.ok(prizeService.exchange(userId(a), req.getPrizeId()));
    }

    private Long userId(Authentication a) {
        try {
            return Long.valueOf(a.getName());
        } catch (Exception e) {
            return -1L;
        }
    }
}
