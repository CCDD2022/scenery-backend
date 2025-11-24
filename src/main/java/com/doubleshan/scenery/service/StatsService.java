package com.doubleshan.scenery.service;

import com.doubleshan.scenery.repository.CheckinRepository;
import com.doubleshan.scenery.repository.PointLedgerRepository;
import com.doubleshan.scenery.repository.UserRepository;
import com.doubleshan.scenery.repository.GiftRedeemRepository;
import com.doubleshan.scenery.repository.GiftRepository;
import com.doubleshan.scenery.repository.MerchantRepository;
import com.doubleshan.scenery.model.PointLedger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatsService {
    private final CheckinRepository checkinRepository;
    private final PointLedgerRepository pointLedgerRepository;
    private final UserRepository userRepository;
    private final GiftRedeemRepository giftRedeemRepository;
    private final GiftRepository giftRepository;
    private final MerchantRepository merchantRepository;

    public StatsService(CheckinRepository cRepo, PointLedgerRepository pRepo, UserRepository uRepo,
            GiftRedeemRepository grRepo,
            GiftRepository gRepo, MerchantRepository mRepo) {
        this.checkinRepository = cRepo;
        this.pointLedgerRepository = pRepo;
        this.userRepository = uRepo;
        this.giftRedeemRepository = grRepo;
        this.giftRepository = gRepo;
        this.merchantRepository = mRepo;
    }

    public Map<String, Object> checkinStats() {
        Map<String, Object> m = new HashMap<>();
        m.put("total_checkins", checkinRepository.findAll().size());
        // 简化: 可扩展热力图数据
        return m;
    }

    public Map<String, Object> pointsStats() {
        Map<String, Object> m = new HashMap<>();
        int totalEarn = pointLedgerRepository.findAll().stream().filter(x -> x.getType().equals("Earn"))
                .mapToInt(PointLedger::getAmount).sum();
        int totalSpend = pointLedgerRepository.findAll().stream().filter(x -> x.getType().equals("Spend"))
                .mapToInt(PointLedger::getAmount).sum();
        m.put("total_earn", totalEarn);
        m.put("total_spend", totalSpend);
        return m;
    }

    public Map<String, Object> globalStats() {
        Map<String, Object> m = new HashMap<>();
        m.put("users_total", userRepository.findAll().size());
        m.put("checkins_total", checkinRepository.findAll().size());
        m.put("gifts_total", giftRepository.findAll().size());
        m.put("gift_redeems_total", giftRedeemRepository.findAll().size());
        m.put("merchants_total", merchantRepository.findAll().size());
        long today = java.time.LocalDate.now().toEpochDay();
        long todayCount = checkinRepository.findAll().stream().filter(c -> java.time.LocalDate
                .ofInstant(c.getCreatedAt(), java.time.ZoneId.systemDefault()).toEpochDay() == today).count();
        m.put("today_checkins", todayCount);
        return m;
    }
}
