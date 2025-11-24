package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.Merchant;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MerchantRepository {
    private final Map<String, Merchant> store = new ConcurrentHashMap<>();

    public Merchant save(Merchant m) {
        store.put(m.getId(), m);
        return m;
    }

    public Optional<Merchant> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Merchant> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(String id) {
        store.remove(id);
    }
}
