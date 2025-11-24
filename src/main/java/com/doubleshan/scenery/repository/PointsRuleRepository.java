package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.PointsRule;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PointsRuleRepository {
    private final Map<String, PointsRule> store = new ConcurrentHashMap<>();

    public PointsRule save(PointsRule r) {
        store.put(r.getId(), r);
        return r;
    }

    public Optional<PointsRule> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<PointsRule> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(String id) {
        store.remove(id);
    }
}
