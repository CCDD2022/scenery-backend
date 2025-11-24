package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.PointsRule;
import com.doubleshan.scenery.repository.PointsRuleRepository;
import com.doubleshan.scenery.response.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointsRuleAdminService {
    private final PointsRuleRepository repository;

    public PointsRuleAdminService(PointsRuleRepository repository) {
        this.repository = repository;
    }

    public List<PointsRule> all() {
        return repository.findAll();
    }

    public PointsRule create(String code, int points, String desc) {
        PointsRule r = new PointsRule(code, points, desc);
        repository.save(r);
        return r;
    }

    public PointsRule update(String id, Integer points, String desc) {
        PointsRule r = repository.findById(id).orElseThrow(() -> new NotFoundException("积分规则不存在"));
        if (points != null) { // 直接通过反射或重新创建，这里简单新建再替换
            PointsRule nr = new PointsRule(r.getCode(), points, desc != null ? desc : r.getDescription());
            try { // 保留原ID (简化处理)
                java.lang.reflect.Field f = PointsRule.class.getDeclaredField("id");
                f.setAccessible(true);
                f.set(nr, r.getId());
            } catch (Exception ignored) {
            }
            repository.save(nr);
            return nr;
        }
        if (desc != null) {
            PointsRule nr = new PointsRule(r.getCode(), r.getPoints(), desc);
            try {
                java.lang.reflect.Field f = PointsRule.class.getDeclaredField("id");
                f.setAccessible(true);
                f.set(nr, r.getId());
            } catch (Exception ignored) {
            }
            repository.save(nr);
            return nr;
        }
        return r;
    }

    public void delete(String id) {
        repository.delete(id);
    }
}
