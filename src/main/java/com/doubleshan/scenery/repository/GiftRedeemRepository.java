package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.GiftRedeem;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class GiftRedeemRepository {
    private final Map<String, GiftRedeem> store = new ConcurrentHashMap<>();

    public GiftRedeem save(GiftRedeem r) {
        store.put(r.getId(), r);
        return r;
    }

    public Optional<GiftRedeem> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<GiftRedeem> findByUser(String userId) {
        return store.values().stream().filter(x -> x.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public Optional<GiftRedeem> findByQr(String qr) {
        return store.values().stream().filter(x -> x.getQrcode().equals(qr)).findFirst();
    }

    public List<GiftRedeem> findAll() {
        return new ArrayList<>(store.values());
    }
}
