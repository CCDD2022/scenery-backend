package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.MapAsset;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MapAssetRepository {
    private final Map<String, MapAsset> store = new ConcurrentHashMap<>();

    public MapAsset save(MapAsset m) {
        store.put(m.getId(), m);
        return m;
    }

    public List<MapAsset> findAll() {
        return new ArrayList<>(store.values());
    }
}
