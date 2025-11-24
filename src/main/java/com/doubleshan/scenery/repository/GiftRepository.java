package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.Gift;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class GiftRepository {
    private final Map<String, Gift> store = new ConcurrentHashMap<>();

    public Gift save(Gift g) {
        store.put(g.getId(), g);
        return g;
    }

    public Optional<Gift> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Gift> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(String id) {
        store.remove(id);
    }
}
