package com.doubleshan.scenery.controller.visitor;

import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.service.RecommendService;
import com.doubleshan.scenery.vo.MerchantVO;
import com.doubleshan.scenery.vo.POIVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommend")
public class RecommendController {
    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @GetMapping("/pois")
    public ApiResponse<List<POIVO>> pois(@RequestParam double lat, @RequestParam double lon,
            @RequestParam(defaultValue = "distance") String sort) {
        List<POIVO> list = recommendService.recommendPois(lat, lon, sort).stream().map(POIVO::from).toList();
        return ApiResponse.ok(list);
    }

    @GetMapping("/merchants")
    public ApiResponse<List<MerchantVO>> merchants(@RequestParam double lat, @RequestParam double lon,
            @RequestParam(defaultValue = "distance") String sort) {
        List<MerchantVO> list = recommendService.recommendMerchants(lat, lon, sort).stream().map(MerchantVO::from)
                .toList();
        return ApiResponse.ok(list);
    }
}
