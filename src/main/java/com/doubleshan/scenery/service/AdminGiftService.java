package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.GiftRedeem;
import com.doubleshan.scenery.repository.GiftRedeemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminGiftService {
    private final GiftRedeemRepository repo;

    public AdminGiftService(GiftRedeemRepository r) {
        this.repo = r;
    }

    public List<GiftRedeem> list(Boolean used) {
        return repo.findAll().stream().filter(r -> used == null || r.isUsed() == used).collect(Collectors.toList());
    }

    public Map<String, Long> stats() {
        long used = repo.findAll().stream().filter(GiftRedeem::isUsed).count();
        long unused = repo.findAll().stream().filter(r -> !r.isUsed()).count();
        return Map.of("used", used, "unused", unused, "total", used + unused);
    }
}