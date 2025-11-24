package com.doubleshan.scenery.service;

import com.doubleshan.scenery.common.exception.BizException;
import com.doubleshan.scenery.dto.CheckInDtos;
import com.doubleshan.scenery.model.*;
import com.doubleshan.scenery.repository.CheckinRepository;
import com.doubleshan.scenery.repository.LikeRecordRepository;
import com.doubleshan.scenery.repository.PointLogRepository;
import com.doubleshan.scenery.repository.UserRepository;
import com.doubleshan.scenery.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckinService {
    private final CheckinRepository checkInRepository;
    private final UserRepository userRepository;
    private final PointLogRepository pointLogRepository;
    private final LikeRecordRepository likeRecordRepository;
    private final SpotRepository spotRepository;

    private static final int CHECKIN_POINTS = 10;
    private static final int LIKE_POINTS = 1;

    @Transactional
    public void create(Long userId, CheckInDtos.CreateCheckInReq req) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BizException("用户不存在"));
        Spot spot = spotRepository.findById(req.getSpotId()).orElseThrow(() -> new BizException("景点不存在"));
        Checkin ci = new Checkin();
        ci.setUser(user);
        ci.setSpot(spot);
        ci.setPhotoFileId(req.getPhotoFileID());
        ci.setComment(req.getComment());
        ci.setPointsEarned(CHECKIN_POINTS);
        // 经纬度可选：未提供则使用景点坐标，距离置0
        Double lat = req.getLat();
        Double lng = req.getLng();
        java.math.BigDecimal checkLat;
        java.math.BigDecimal checkLng;
        java.math.BigDecimal distance;
        if (lat == null || lng == null) {
            checkLat = spot.getLat();
            checkLng = spot.getLng();
            distance = java.math.BigDecimal.ZERO.setScale(2);
        } else {
            checkLat = java.math.BigDecimal.valueOf(lat);
            checkLng = java.math.BigDecimal.valueOf(lng);
            double d = haversine(lat, lng, spot.getLat().doubleValue(), spot.getLng().doubleValue());
            distance = java.math.BigDecimal.valueOf(d).setScale(2, java.math.RoundingMode.HALF_UP);
        }
        ci.setCheckLat(checkLat);
        ci.setCheckLng(checkLng);
        ci.setDistance(distance);
        checkInRepository.save(ci);
        // 积分更新
        user.setTotalPoints(user.getTotalPoints() + CHECKIN_POINTS);
        user.setCheckInCount(user.getCheckInCount() + 1);
        userRepository.save(user);
        PointLog pl = new PointLog();
        pl.setUser(user);
        pl.setType(PointLogType.check_in);
        pl.setPoints(CHECKIN_POINTS);
        pl.setBalance(user.getTotalPoints());
        pl.setSourceId(ci.getId());
        pl.setDescription("打卡奖励");
        pointLogRepository.save(pl);
    }

    @Transactional
    public CheckInDtos.LikeResp like(Long userId, Long checkInId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BizException("用户不存在"));
        Checkin ci = checkInRepository.findById(checkInId).orElseThrow(() -> new BizException("打卡不存在"));
        likeRecordRepository.findByUserAndCheckIn(user, ci).ifPresent(l -> {
            throw new BizException("已点赞");
        });
        LikeRecord lr = new LikeRecord();
        lr.setUser(user);
        lr.setCheckIn(ci);
        likeRecordRepository.save(lr);
        // 积分+1
        user.setTotalPoints(user.getTotalPoints() + LIKE_POINTS);
        userRepository.save(user);
        PointLog pl = new PointLog();
        pl.setUser(user);
        pl.setType(PointLogType.like);
        pl.setPoints(LIKE_POINTS);
        pl.setBalance(user.getTotalPoints());
        pl.setSourceId(ci.getId());
        pl.setDescription("点赞获得积分");
        pointLogRepository.save(pl);
        CheckInDtos.LikeResp resp = new CheckInDtos.LikeResp();
        resp.setCheckInId(ci.getId());
        resp.setLikeCount(likeRecordRepository.countByCheckIn(ci));
        return resp;
    }

    public Page<Checkin> myCheckIns(Long userId, int page) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BizException("用户不存在"));
        return checkInRepository.findByUser(user, PageRequest.of(page, 10));
    }

    public Page<Checkin> feed(int page) {
        return checkInRepository.findAll(PageRequest.of(page, 10));
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371000d;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
