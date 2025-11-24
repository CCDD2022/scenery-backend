package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.Supplier;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SupplierRepository {
    private final Map<String, Supplier> store = new ConcurrentHashMap<>();

    public Supplier save(Supplier s) {
        store.put(s.getId(), s);
        return s;
    }

    public List<Supplier> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Supplier> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}
