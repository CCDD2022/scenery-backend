package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.PointLedger;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class PointLedgerRepository {
    private final Map<String, PointLedger> store = new ConcurrentHashMap<>();

    public PointLedger save(PointLedger p) {
        store.put(p.getId(), p);
        return p;
    }

    public List<PointLedger> findByUser(String userId) {
        return store.values().stream().filter(x -> x.getUserId().equals(userId))
                .sorted(Comparator.comparing(PointLedger::getCreatedAt).reversed()).collect(Collectors.toList());
    }

    public List<PointLedger> findAll() {
        return store.values().stream().sorted(Comparator.comparing(PointLedger::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }
}
