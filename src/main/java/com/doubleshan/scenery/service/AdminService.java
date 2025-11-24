package com.doubleshan.scenery.service;

import com.doubleshan.scenery.common.exception.BizException;
import com.doubleshan.scenery.dto.PrizeDtos;
import com.doubleshan.scenery.model.*;
import com.doubleshan.scenery.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PointLogRepository pointLogRepository;
    private final ExchangeRecordRepository exchangeRecordRepository;

    public Page<User> userList(int page, String keyword) {
        Pageable pageable = PageRequest.of(page, 10);
        if (keyword == null || keyword.isBlank()) {
            return userRepository.findAll(pageable);
        }
        Specification<User> spec = (root, query, cb) -> cb.or(
                cb.like(root.get("username"), "%" + keyword + "%"),
                cb.like(root.get("nickName"), "%" + keyword + "%"));
        return userRepository.findAll(spec, pageable);
    }

    @Transactional
    public void adjustPoints(PrizeDtos.AdjustPointsReq req) {
        User user = userRepository.findById(req.getUserId()).orElseThrow(() -> new BizException("用户不存在"));
        user.setTotalPoints(user.getTotalPoints() + req.getPoints());
        userRepository.save(user);
        PointLog pl = new PointLog();
        pl.setUser(user);
        pl.setType(PointLogType.admin_adjust);
        pl.setPoints(req.getPoints());
        pl.setBalance(user.getTotalPoints());
        pl.setDescription(req.getReason() == null ? "管理员调整" : req.getReason());
        pointLogRepository.save(pl);
    }

    @Transactional
    public ExchangeRecord verifyPrize(String code) {
        ExchangeRecord er = exchangeRecordRepository.findByExchangeCode(code)
                .orElseThrow(() -> new BizException("兑换码错误"));
        if (er.getStatus() != ExchangeStatus.pending)
            throw new BizException("不可核销");
        er.setStatus(ExchangeStatus.used);
        er.setUsedAt(java.time.Instant.now());
        return exchangeRecordRepository.save(er);
    }
}
