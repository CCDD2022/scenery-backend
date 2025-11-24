package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.PointLedger;
import com.doubleshan.scenery.model.PointsRule;
import com.doubleshan.scenery.model.User;
import com.doubleshan.scenery.repository.PointLedgerRepository;
import com.doubleshan.scenery.repository.PointsRuleRepository;
import com.doubleshan.scenery.repository.UserRepository;
import com.doubleshan.scenery.response.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PointsService {
    private final PointLedgerRepository ledgerRepository;
    private final PointsRuleRepository ruleRepository;
    private final UserRepository userRepository;

    public PointsService(PointLedgerRepository l, PointsRuleRepository r, UserRepository u) {
        this.ledgerRepository = l;
        this.ruleRepository = r;
        this.userRepository = u;
    }

    public void earn(String userId, String code) {
        User u = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("用户不存在"));
        PointsRule rule = ruleRepository.findAll().stream().filter(x -> x.getCode().equals(code)).findFirst()
                .orElse(null);
        if (rule == null)
            return; // 未配置则忽略
        u.addPoints(rule.getPoints());
        ledgerRepository.save(new PointLedger(userId, "Earn", rule.getPoints(), code));
    }

    public void spend(String userId, int amount, String code) {
        User u = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("用户不存在"));
        if (u.getPoints() < amount)
            throw new IllegalArgumentException("积分不足");
        u.spendPoints(amount);
        ledgerRepository.save(new PointLedger(userId, "Spend", amount, code));
    }

    public List<PointLedger> ledger(String userId) {
        return ledgerRepository.findByUser(userId);
    }

    public Map<String, Object> stats(String userId) {
        int earned = ledger(userId).stream().filter(x -> x.getType().equals("Earn")).mapToInt(PointLedger::getAmount)
                .sum();
        int spent = ledger(userId).stream().filter(x -> x.getType().equals("Spend")).mapToInt(PointLedger::getAmount)
                .sum();
        User u = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("用户不存在"));
        Map<String, Object> m = new HashMap<>();
        m.put("earned", earned);
        m.put("spent", spent);
        m.put("balance", u.getPoints());
        return m;
    }

    public List<PointsRule> rules() {
        return ruleRepository.findAll();
    }
}
