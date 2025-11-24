package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.Review;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class ReviewRepository {
    private final Map<String, Review> store = new ConcurrentHashMap<>();

    public Review save(Review r) {
        store.put(r.getId(), r);
        return r;
    }

    public List<Review> findByPoi(String poiId) {
        return store.values().stream().filter(x -> x.getPoiId().equals(poiId))
                .sorted(Comparator.comparing(Review::getCreatedAt).reversed()).collect(Collectors.toList());
    }

    public List<Review> recent(int limit) {
        return store.values().stream().sorted(Comparator.comparing(Review::getCreatedAt).reversed()).limit(limit)
                .collect(Collectors.toList());
    }
}
