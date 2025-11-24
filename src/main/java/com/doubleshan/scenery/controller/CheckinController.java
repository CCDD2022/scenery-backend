package com.doubleshan.scenery.controller;

import com.doubleshan.scenery.common.api.ApiResponse;
import com.doubleshan.scenery.dto.CheckInDtos;
import com.doubleshan.scenery.model.Checkin;
import com.doubleshan.scenery.service.CheckinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkin")
@RequiredArgsConstructor
public class CheckinController {
    private final CheckinService checkInService;

    @PostMapping("/create")
    public ApiResponse<Void> create(@RequestBody @Valid CheckInDtos.CreateCheckInReq req, Authentication a) {
        checkInService.create(userId(a), req);
        return ApiResponse.ok(null);
    }

    @PostMapping("/like")
    public ApiResponse<CheckInDtos.LikeResp> like(@RequestBody @Valid CheckInDtos.LikeReq req, Authentication a) {
        return ApiResponse.ok(checkInService.like(userId(a), req.getCheckInId()));
    }

    @GetMapping("/my")
    public ApiResponse<Page<Checkin>> my(@RequestParam(defaultValue = "0") int page, Authentication a) {
        return ApiResponse.ok(checkInService.myCheckIns(userId(a), page));
    }

    @GetMapping("/feed")
    public ApiResponse<Page<Checkin>> feed(@RequestParam(defaultValue = "0") int page) {
        return ApiResponse.ok(checkInService.feed(page));
    }

    private Long userId(Authentication a) {
        try {
            return Long.valueOf(a.getName());
        } catch (Exception e) {
            return -1L;
        }
    }
}
