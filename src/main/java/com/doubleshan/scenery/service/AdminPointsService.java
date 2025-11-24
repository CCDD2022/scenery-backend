package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.PointLedger;
import com.doubleshan.scenery.repository.PointLedgerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminPointsService {
    private final PointLedgerRepository repo;

    public AdminPointsService(PointLedgerRepository r) {
        this.repo = r;
    }

    public List<PointLedger> list(String type, String userId) {
        return repo.findAll().stream()
                .filter(p -> type == null || p.getType().equalsIgnoreCase(type))
                .filter(p -> userId == null || p.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Map<String, Integer> stats() {
        int earn = repo.findAll().stream().filter(p -> p.getType().equals("Earn")).mapToInt(PointLedger::getAmount)
                .sum();
        int spend = repo.findAll().stream().filter(p -> p.getType().equals("Spend")).mapToInt(PointLedger::getAmount)
                .sum();
        return Map.of("total_earn", earn, "total_spend", spend);
    }
}