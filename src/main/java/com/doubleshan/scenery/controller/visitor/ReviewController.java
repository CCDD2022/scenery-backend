package com.doubleshan.scenery.controller.visitor;

import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.SecurityContext;
import com.doubleshan.scenery.vo.ReviewVO;
import com.doubleshan.scenery.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    static class CreateReq {
        public String poi_id;
        public String checkin_id;
        public int stars;
        public String content;
    }

    @PostMapping
    public ApiResponse<ReviewVO> create(@RequestBody CreateReq req) {
        String userId = SecurityContext.getUserId();
        return ApiResponse
                .ok(ReviewVO.from(reviewService.create(userId, req.poi_id, req.checkin_id, req.stars, req.content)));
    }

    @GetMapping
    public ApiResponse<List<ReviewVO>> list(@RequestParam("poi_id") String poiId) {
        return ApiResponse.ok(reviewService.listByPoi(poiId).stream().map(ReviewVO::from).toList());
    }

    @GetMapping("/dynamics")
    public ApiResponse<List<ReviewVO>> dynamics(@RequestParam(value = "limit", defaultValue = "50") int limit) {
        return ApiResponse.ok(reviewService.dynamics(limit).stream().map(ReviewVO::from).toList());
    }
}
