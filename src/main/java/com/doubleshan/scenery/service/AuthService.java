package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.User;
import com.doubleshan.scenery.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginOrRegister(String openId, String nickname, String avatar) {
        return userRepository.findByOpenId(openId).orElseGet(() -> {
            User u = new User(openId, nickname, avatar);
            userRepository.save(u);
            return u;
        });
    }
}
