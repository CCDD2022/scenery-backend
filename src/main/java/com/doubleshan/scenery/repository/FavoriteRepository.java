package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.Favorite;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class FavoriteRepository {
    private final Map<String, Favorite> store = new ConcurrentHashMap<>();

    public Favorite save(Favorite f) {
        store.put(f.getId(), f);
        return f;
    }

    public Optional<Favorite> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Favorite> findByUser(String userId) {
        return store.values().stream().filter(x -> x.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public void delete(String id) {
        store.remove(id);
    }

    public Optional<Favorite> findByUserAndPoi(String userId, String poiId) {
        return store.values().stream().filter(x -> x.getUserId().equals(userId) && x.getPoiId().equals(poiId))
                .findFirst();
    }
}
