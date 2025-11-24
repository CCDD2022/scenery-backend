package com.doubleshan.scenery.controller.visitor;

import com.doubleshan.scenery.model.POI;
import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.service.POIService;
import com.doubleshan.scenery.util.DistanceUtil;
import com.doubleshan.scenery.vo.POIVO;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pois")
public class POIController {
    private final POIService poiService;

    public POIController(POIService poiService) {
        this.poiService = poiService;
    }

    // 列表接口: 支持按分类筛选与距离/热度排序
    @GetMapping
    public ApiResponse<List<POIVO>> list(@RequestParam(required = false) String type,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(defaultValue = "heat") String sort) {
        List<POI> all = poiService.all();
        // 分类筛选
        if (type != null && !type.isEmpty()) {
            all = all.stream().filter(p -> type.equalsIgnoreCase(p.getType())).collect(Collectors.toList());
        }
        // 构建 VO 列表并计算距离
        List<POIVO> voList = all.stream().map(p -> {
            Double distance = null;
            if (lat != null && lon != null) {
                distance = DistanceUtil.distanceMeters(lat, lon, p.getLatitude(), p.getLongitude());
            }
            return POIVO.fromWithDistance(p, distance);
        }).collect(Collectors.toList());
        // 排序
        if (lat != null && lon != null && "distance".equalsIgnoreCase(sort)) {
            voList = voList.stream()
                    .sorted(Comparator.comparing(v -> v.distanceMeters == null ? Double.MAX_VALUE : v.distanceMeters))
                    .collect(Collectors.toList());
        } else { // 默认热度
            voList = voList.stream().sorted(Comparator.comparingInt((POIVO v) -> v.heat).reversed())
                    .collect(Collectors.toList());
        }
        return ApiResponse.ok(voList);
    }

    // 详情接口: 返回距离与分类
    @GetMapping("/{id}")
    public ApiResponse<POIVO> detail(@PathVariable String id,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon) {
        POI p = poiService.get(id);
        Double distance = null;
        if (lat != null && lon != null) {
            distance = DistanceUtil.distanceMeters(lat, lon, p.getLatitude(), p.getLongitude());
        }
        return ApiResponse.ok(POIVO.fromWithDistance(p, distance));
    }
}
