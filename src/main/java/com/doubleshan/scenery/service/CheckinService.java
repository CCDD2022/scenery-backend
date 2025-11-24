package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.Checkin;
import com.doubleshan.scenery.model.POI;
import com.doubleshan.scenery.repository.CheckinRepository;
import com.doubleshan.scenery.repository.POIRepository;
import com.doubleshan.scenery.response.ConflictException;
import com.doubleshan.scenery.response.NotFoundException;
import com.doubleshan.scenery.util.DistanceUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckinService {
    private final CheckinRepository checkinRepository;
    private final POIRepository poiRepository;

    public CheckinService(CheckinRepository cRepo, POIRepository pRepo) {
        this.checkinRepository = cRepo;
        this.poiRepository = pRepo;
    }

    public Checkin create(String userId, String poiId, double lat, double lon, String photo) {
        POI poi = poiRepository.findById(poiId).orElseThrow(() -> new NotFoundException("POI不存在"));
        double dist = DistanceUtil.distanceMeters(lat, lon, poi.getLatitude(), poi.getLongitude());
        if (dist > 200)
            throw new ConflictException("距离过远，无法打卡");
        Checkin c = new Checkin(userId, poiId, lat, lon, photo);
        checkinRepository.save(c);
        poi.incHeat();
        return c;
    }

    public void like(String id, String userId) {
        Checkin c = checkinRepository.findById(id).orElseThrow(() -> new NotFoundException("打卡不存在"));
        c.like(userId);
    }

    public List<Checkin> userCheckins(String userId) {
        return checkinRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Checkin> dynamics(int limit) {
        return checkinRepository.findAllByOrderByCreatedAtDesc(org.springframework.data.domain.PageRequest.of(0, limit))
                .getContent();
    }
}
