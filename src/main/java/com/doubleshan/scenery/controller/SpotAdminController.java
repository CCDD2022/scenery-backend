package com.doubleshan.scenery.controller;

import com.doubleshan.scenery.common.api.ApiResponse;
import com.doubleshan.scenery.model.Spot;
import com.doubleshan.scenery.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/spot")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SpotAdminController {
    private final SpotRepository spotRepository;

    @PostMapping("/createSpot")
    public ApiResponse<Spot> create(@RequestBody Spot spot) {
        spot.setId(null);
        return ApiResponse.ok(spotRepository.save(spot));
    }

    @PutMapping("/updateSpot/{id}")
    public ApiResponse<Spot> update(@PathVariable Long id, @RequestBody Spot spot) {
        spot.setId(id);
        return ApiResponse.ok(spotRepository.save(spot));
    }

    @DeleteMapping("/deleteSpot/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        spotRepository.deleteById(id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/getAllSpots")
    public ApiResponse<List<Spot>> all() {
        return ApiResponse.ok(spotRepository.findAll());
    }
}
