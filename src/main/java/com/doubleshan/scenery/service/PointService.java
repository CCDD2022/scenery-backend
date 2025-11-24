package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.PointLog;
import com.doubleshan.scenery.model.User;
import com.doubleshan.scenery.repository.PointLogRepository;
import com.doubleshan.scenery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointLogRepository pointLogRepository;
    private final UserRepository userRepository;

    public Page<PointLog> list(Long userId, int page) {
        User user = userRepository.findById(userId).orElseThrow();
        return pointLogRepository.findByUserOrderByIdDesc(user, PageRequest.of(page, 10));
    }
}
