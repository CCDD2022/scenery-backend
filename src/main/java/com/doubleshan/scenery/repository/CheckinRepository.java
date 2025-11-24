package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.Checkin;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class CheckinRepository {
    private final Map<String, Checkin> store = new ConcurrentHashMap<>();

    public Checkin save(Checkin c) {
        store.put(c.getId(), c);
        return c;
    }

    public Optional<Checkin> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Checkin> findAll() {
        return new ArrayList<>(store.values());
    }

    public List<Checkin> findByUser(String userId) {
        return store.values().stream().filter(x -> x.getUserId().equals(userId))
                .sorted(Comparator.comparing(Checkin::getCreatedAt).reversed()).collect(Collectors.toList());
    }

    public List<Checkin> recent(int limit) {
        return store.values().stream().sorted(Comparator.comparing(Checkin::getCreatedAt).reversed()).limit(limit)
                .collect(Collectors.toList());
    }

    public void delete(String id) {
        store.remove(id);
    }

    public List<Checkin> findByPoi(String poiId) {
        return store.values().stream().filter(x -> x.getPoiId().equals(poiId))
                .sorted(Comparator.comparing(Checkin::getCreatedAt).reversed()).collect(Collectors.toList());
    }
}
