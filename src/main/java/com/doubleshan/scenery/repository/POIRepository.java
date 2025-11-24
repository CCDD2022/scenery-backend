package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.POI;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class POIRepository {
    private final Map<String, POI> store = new ConcurrentHashMap<>();

    public POI save(POI p) {
        store.put(p.getId(), p);
        return p;
    }

    public Optional<POI> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<POI> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(String id) {
        store.remove(id);
    }
}
