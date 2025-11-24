package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.Checkin;
import com.doubleshan.scenery.model.User;
import com.doubleshan.scenery.repository.CheckinRepository;
import com.doubleshan.scenery.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RankingService {
    private final UserRepository userRepository;
    private final CheckinRepository checkinRepository;

    public RankingService(UserRepository uRepo, CheckinRepository cRepo) {
        this.userRepository = uRepo;
        this.checkinRepository = cRepo;
    }

    public List<Map<String, Object>> rankings() {
        List<User> users = userRepository.findAll();
        Map<String, List<Checkin>> byUser = checkinRepository.findAll().stream()
                .collect(Collectors.groupingBy(Checkin::getUserId));
        List<Map<String, Object>> result = new ArrayList<>();
        for (User u : users) {
            List<Checkin> cs = byUser.getOrDefault(u.getId(), Collections.emptyList());
            int checkinCount = cs.size();
            int likeCount = cs.stream().mapToInt(Checkin::getLikeCount).sum();
            int score = checkinCount * 10 + likeCount;
            Map<String, Object> row = new HashMap<>();
            row.put("user_id", u.getId());
            row.put("nickname", u.getNickname());
            row.put("score", score);
            row.put("checkins", checkinCount);
            row.put("likes", likeCount);
            result.add(row);
        }
        result.sort((a, b) -> Integer.compare((int) b.get("score"), (int) a.get("score")));
        return result;
    }

    public Map<String, Object> my(String userId) {
        List<Map<String, Object>> r = rankings();
        for (int i = 0; i < r.size(); i++) {
            if (r.get(i).get("user_id").equals(userId)) {
                Map<String, Object> m = new HashMap<>(r.get(i));
                m.put("rank", i + 1);
                return m;
            }
        }
        return Collections.emptyMap();
    }
}
