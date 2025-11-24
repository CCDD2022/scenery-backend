package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.Checkin;
import com.doubleshan.scenery.repository.CheckinRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminCheckinService {
    private final CheckinRepository checkinRepository;

    public AdminCheckinService(CheckinRepository repo) {
        this.checkinRepository = repo;
    }

    public List<Checkin> list(String poiId, Instant start, Instant end, String userId) {
        return checkinRepository.findAll().stream()
                .filter(c -> poiId == null || c.getPoiId().equals(poiId))
                .filter(c -> userId == null || c.getUserId().equals(userId))
                .filter(c -> start == null || !c.getCreatedAt().isBefore(start))
                .filter(c -> end == null || !c.getCreatedAt().isAfter(end))
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public void delete(String id) {
        checkinRepository.deleteById(id);
    }

    public Map<String, Long> statsByPoi() {
        return checkinRepository.findAll().stream()
                .collect(Collectors.groupingBy(Checkin::getPoiId, Collectors.counting()));
    }
}