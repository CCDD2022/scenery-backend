package com.doubleshan.scenery.controller.visitor;

import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.service.MapService;
import com.doubleshan.scenery.vo.MapAssetVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/map")
public class MapController {
    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/handdrawn")
    public ApiResponse<List<MapAssetVO>> assets() {
        List<MapAssetVO> list = mapService.assets().stream().map(MapAssetVO::from).toList();
        return ApiResponse.ok(list);
    }
}
