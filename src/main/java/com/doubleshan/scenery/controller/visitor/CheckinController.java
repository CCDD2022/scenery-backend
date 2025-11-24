package com.doubleshan.scenery.controller.visitor;

import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.SecurityContext;
import com.doubleshan.scenery.vo.CheckinVO;
import com.doubleshan.scenery.service.CheckinService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CheckinController {
    private final CheckinService checkinService;

    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    static class CreateReq {
        public String poi_id;
        public double latitude;
        public double longitude;
        public String photo;
    }

    @PostMapping("/checkins")
    public ApiResponse<CheckinVO> create(@RequestBody CreateReq req) {
        String userId = SecurityContext.getUserId();
        return ApiResponse
                .ok(CheckinVO.from(checkinService.create(userId, req.poi_id, req.latitude, req.longitude, req.photo)));
    }

    @PostMapping("/checkins/{id}/like")
    public ApiResponse<Void> like(@PathVariable String id) {
        String userId = SecurityContext.getUserId();
        checkinService.like(id, userId);
        return ApiResponse.ok(null);
    }

    @GetMapping("/dynamics")
    public ApiResponse<List<CheckinVO>> dynamics() {
        return ApiResponse.ok(checkinService.dynamics(50).stream().map(CheckinVO::from).toList());
    }
}
