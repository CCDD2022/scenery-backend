package com.doubleshan.scenery.controller;

import com.doubleshan.scenery.common.api.ApiResponse;
import com.doubleshan.scenery.dto.PrizeDtos;
import com.doubleshan.scenery.dto.AdminDtos;
import com.doubleshan.scenery.model.ExchangeRecord;
import com.doubleshan.scenery.model.User;
import com.doubleshan.scenery.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    public ApiResponse<java.util.List<AdminDtos.UserSummaryResp>> users(@RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="keyword",required = false) String keyword) {
        Page<User> p = adminService.userList(page, keyword);
        java.util.List<AdminDtos.UserSummaryResp> list = p.stream().map(u -> {
            AdminDtos.UserSummaryResp r = new AdminDtos.UserSummaryResp();
            r.setUserId(u.getId());
            r.setUsername(u.getUsername());
            r.setNickName(u.getNickName());
            r.setPhone(u.getPhone());
            r.setPoints(u.getTotalPoints());
            r.setCheckInCount(u.getCheckInCount());
            return r;
        }).toList();
        return ApiResponse.ok(list);
    }

    @PostMapping("/adjustPoints")
    public ApiResponse<Void> adjust(@RequestBody @Valid PrizeDtos.AdjustPointsReq req) {
        adminService.adjustPoints(req);
        return ApiResponse.ok(null);
    }

    @PostMapping("/verifyPrize/{code}")
    public ApiResponse<AdminDtos.VerifyPrizeResp> verify(@PathVariable String code) {
        ExchangeRecord er = adminService.verifyPrize(code);
        AdminDtos.VerifyPrizeResp resp = new AdminDtos.VerifyPrizeResp();
        resp.setUserId(er.getUser().getId());
        resp.setUserNickName(er.getUser().getNickName());
        resp.setPrizeInfo(er.getPrize().getName());
        return ApiResponse.ok(resp);
    }
}
