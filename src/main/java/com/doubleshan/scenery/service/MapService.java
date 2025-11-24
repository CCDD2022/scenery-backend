package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.MapAsset;
import com.doubleshan.scenery.repository.MapAssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapService {
    private final MapAssetRepository mapAssetRepository;

    public MapService(MapAssetRepository mapAssetRepository) {
        this.mapAssetRepository = mapAssetRepository;
    }

    public List<MapAsset> assets() {
        return mapAssetRepository.findAll();
    }
}
