package com.doubleshan.scenery.controller;

import com.doubleshan.scenery.common.api.ApiResponse;
import com.doubleshan.scenery.dto.SpotDtos;
import com.doubleshan.scenery.service.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {
    private final SpotService spotService;

    @GetMapping("/introduction")
    public ApiResponse<String> intro() {
        return ApiResponse.ok("引领区简介占位内容");
    }

    @GetMapping({ "/spots", "/getSpotList" })
    public ApiResponse<List<SpotDtos.SpotResp>> spotList(@RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng) {
        List<SpotDtos.SpotResp> list = spotService.listActive().stream().map(s -> {
            SpotDtos.SpotResp r = new SpotDtos.SpotResp();
            r.setId(s.getId());
            r.setName(s.getName());
            r.setLat(s.getLat() == null ? null : s.getLat().doubleValue());
            r.setLng(s.getLng() == null ? null : s.getLng().doubleValue());
            r.setDescription(s.getDescription());
            r.setImage(s.getImage());
            return r;
        }).collect(Collectors.toList());
        return ApiResponse.ok(list);
    }

    @GetMapping({ "/nearby", "/getNearbySpots" })
    public ApiResponse<List<SpotDtos.SpotResp>> nearby(@RequestParam Double lat, @RequestParam Double lng,
            @RequestParam(defaultValue = "5") int limit) {
        List<SpotDtos.SpotResp> list = spotService.listActive().stream()
                .sorted((a, b) -> Double.compare(distance(lat, lng, a.getLat().doubleValue(), a.getLng().doubleValue()),
                        distance(lat, lng, b.getLat().doubleValue(), b.getLng().doubleValue())))
                .limit(limit)
                .map(s -> {
                    SpotDtos.SpotResp r = new SpotDtos.SpotResp();
                    r.setId(s.getId());
                    r.setName(s.getName());
                    r.setLat(s.getLat() == null ? null : s.getLat().doubleValue());
                    r.setLng(s.getLng() == null ? null : s.getLng().doubleValue());
                    r.setDescription(s.getDescription());
                    r.setImage(s.getImage());
                    return r;
                }).collect(Collectors.toList());
        return ApiResponse.ok(list);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371000d;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
