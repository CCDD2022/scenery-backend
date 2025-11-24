package com.doubleshan.scenery.service;

import com.doubleshan.scenery.common.exception.BizException;
import com.doubleshan.scenery.dto.UserDtos;
import com.doubleshan.scenery.model.User;
import com.doubleshan.scenery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BizException("用户不存在"));
    }

    @Transactional
    public void updateAvatar(Long userId, String avatar) {
        User user = get(userId);
        user.setAvatarUrl(avatar);
        userRepository.save(user);
    }

    public UserDtos.ProfileResp profile(Long userId) {
        User user = get(userId);
        UserDtos.ProfileResp resp = new UserDtos.ProfileResp();
        resp.setUserId(user.getId());
        resp.setNickName(user.getNickName());
        resp.setAvatar(user.getAvatarUrl());
        resp.setPhone(user.getPhone());
        resp.setTotalPoints(user.getTotalPoints());
        resp.setCheckInCount(user.getCheckInCount());
        return resp;
    }
}
