package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.Gift;
import com.doubleshan.scenery.model.GiftRedeem;
import com.doubleshan.scenery.repository.GiftRedeemRepository;
import com.doubleshan.scenery.repository.GiftRepository;
import com.doubleshan.scenery.repository.UserRepository;
import com.doubleshan.scenery.response.ConflictException;
import com.doubleshan.scenery.response.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftService {
    private final GiftRepository giftRepository;
    private final GiftRedeemRepository redeemRepository;
    private final UserRepository userRepository;
    private final PointsService pointsService;

    public GiftService(GiftRepository giftRepository, GiftRedeemRepository redeemRepository,
            UserRepository userRepository, PointsService pointsService) {
        this.giftRepository = giftRepository;
        this.redeemRepository = redeemRepository;
        this.userRepository = userRepository;
        this.pointsService = pointsService;
    }

    public List<Gift> list() {
        return giftRepository.findAll();
    }

    public Gift get(String id) {
        return giftRepository.findById(id).orElseThrow(() -> new NotFoundException("礼品不存在"));
    }

    public Gift create(Gift g) {
        return giftRepository.save(g);
    }

    public Gift update(String id, String name, Integer cost, Integer stock) {
        Gift g = get(id);
        if (name != null)
            g.setName(name);
        if (cost != null)
            g.setPointsCost(cost);
        if (stock != null)
            g.setStock(stock);
        return g;
    }

    public void delete(String id) {
        giftRepository.delete(id);
    }

    public GiftRedeem redeem(String giftId, String userId) {
        Gift g = get(giftId);
        if (g.getStock() <= 0)
            throw new ConflictException("库存不足");
        com.doubleshan.scenery.model.User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("用户不存在"));
        if (user.getPoints() < g.getPointsCost())
            throw new ConflictException("积分不足");
        g.decStock();
        pointsService.spend(userId, g.getPointsCost(), "REDEEM");
        GiftRedeem r = new GiftRedeem(giftId, userId);
        redeemRepository.save(r);
        return r;
    }

    public List<GiftRedeem> myRedeems(String userId) {
        return redeemRepository.findByUser(userId);
    }

    public GiftRedeem verify(String redeemId, String merchantId) {
        GiftRedeem r = redeemRepository.findById(redeemId).orElseThrow(() -> new NotFoundException("记录不存在"));
        if (r.isUsed())
            throw new ConflictException("已核销");
        r.verify(merchantId);
        return r;
    }

    public List<GiftRedeem> allRedeems() {
        return redeemRepository.findAll();
    }
}
