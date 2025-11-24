package com.doubleshan.scenery.controller.visitor;

import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.SecurityContext;
import com.doubleshan.scenery.service.RankingService;
import com.doubleshan.scenery.vo.RankingEntryVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rankings")
public class RankingController {
    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public ApiResponse<List<RankingEntryVO>> list() {
        List<RankingEntryVO> list = rankingService.rankings().stream().map(RankingEntryVO::from).toList();
        return ApiResponse.ok(list);
    }

    @GetMapping("/me")
    public ApiResponse<RankingEntryVO> me() {
        String userId = SecurityContext.getUserId();
        return ApiResponse.ok(RankingEntryVO.from(rankingService.my(userId)));
    }
}
