package com.doubleshan.scenery.controller.visitor;

import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.SecurityContext;
import com.doubleshan.scenery.service.PointsService;
import com.doubleshan.scenery.vo.PointLedgerVO;
import com.doubleshan.scenery.vo.PointsRuleVO;
import com.doubleshan.scenery.vo.PointsStatsVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/points")
public class PointsController {
    private final PointsService pointsService;

    public PointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @GetMapping("/ledger")
    public ApiResponse<List<PointLedgerVO>> ledger() {
        String userId = SecurityContext.getUserId();
        List<PointLedgerVO> list = pointsService.ledger(userId).stream().map(PointLedgerVO::from).toList();
        return ApiResponse.ok(list);
    }

    @GetMapping("/stats")
    public ApiResponse<PointsStatsVO> stats() {
        String userId = SecurityContext.getUserId();
        Map<String, Object> m = pointsService.stats(userId);
        return ApiResponse.ok(PointsStatsVO.from(m));
    }

    @GetMapping("/rules")
    public ApiResponse<List<PointsRuleVO>> rules() {
        List<PointsRuleVO> list = pointsService.rules().stream().map(PointsRuleVO::from).toList();
        return ApiResponse.ok(list);
    }
}
