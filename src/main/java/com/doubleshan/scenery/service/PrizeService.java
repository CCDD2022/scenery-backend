package com.doubleshan.scenery.service;

import com.doubleshan.scenery.common.exception.BizException;
import com.doubleshan.scenery.dto.PrizeDtos;
import com.doubleshan.scenery.model.*;
import com.doubleshan.scenery.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrizeService {
    private final PrizeRepository prizeRepository;
    private final UserRepository userRepository;
    private final ExchangeRecordRepository exchangeRecordRepository;
    private final PointLogRepository pointLogRepository;

    public List<Prize> list() {
        return prizeRepository.findByStatus(1);
    }

    @Transactional
    public PrizeDtos.ExchangeResp exchange(Long userId, Long prizeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BizException("用户不存在"));
        Prize prize = prizeRepository.findById(prizeId).orElseThrow(() -> new BizException("奖品不存在"));
        if (prize.getStatus() != 1)
            throw new BizException("奖品未上架");
        if (prize.getStock() <= 0)
            throw new BizException("库存不足");
        if (user.getTotalPoints() < prize.getPoints())
            throw new BizException("积分不足");
        user.setTotalPoints(user.getTotalPoints() - prize.getPoints());
        userRepository.save(user);
        prize.setStock(prize.getStock() - 1);
        prizeRepository.save(prize);
        ExchangeRecord er = new ExchangeRecord();
        er.setUser(user);
        er.setPrize(prize);
        er.setExchangeCode(RandomStringUtils.randomAlphanumeric(8));
        er.setStatus(ExchangeStatus.pending);
        exchangeRecordRepository.save(er);
        PointLog pl = new PointLog();
        pl.setUser(user);
        pl.setType(PointLogType.exchange);
        pl.setPoints(-prize.getPoints());
        pl.setBalance(user.getTotalPoints());
        pl.setSourceId(er.getId());
        pl.setDescription("兑换奖品");
        pointLogRepository.save(pl);
        PrizeDtos.ExchangeResp resp = new PrizeDtos.ExchangeResp();
        resp.setExchangeCode(er.getExchangeCode());
        return resp;
    }

    @Transactional
    public void verify(String code) {
        ExchangeRecord er = exchangeRecordRepository.findByExchangeCode(code)
                .orElseThrow(() -> new BizException("兑换码不存在"));
        if (er.getStatus() != ExchangeStatus.pending)
            throw new BizException("状态不可核销");
        er.setStatus(ExchangeStatus.used);
        er.setUsedAt(java.time.Instant.now());
        exchangeRecordRepository.save(er);
    }
}
