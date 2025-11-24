package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.POI;
import com.doubleshan.scenery.repository.POIRepository;
import com.doubleshan.scenery.response.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POIService {
    private final POIRepository poiRepository;

    public POIService(POIRepository poiRepository) {
        this.poiRepository = poiRepository;
    }

    public POI create(POI p) {
        return poiRepository.save(p);
    }

    public POI get(String id) {
        return poiRepository.findById(id).orElseThrow(() -> new NotFoundException("POI不存在"));
    }

    public List<POI> all() {
        return poiRepository.findAll();
    }

    public POI update(String id, String name, String desc, String media) {
        POI p = get(id);
        if (name != null)
            p.setDescription(desc); // 简化
        if (desc != null)
            p.setDescription(desc);
        if (media != null)
            p.setMedia(media);
        return p;
    }

    public void delete(String id) {
        poiRepository.deleteById(id);
    }
}
