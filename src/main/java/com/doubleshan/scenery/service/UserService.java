package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.User;
import com.doubleshan.scenery.repository.UserRepository;
import com.doubleshan.scenery.response.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("用户不存在"));
    }

    public User update(String id, String nickname, String avatar, String phone) {
        User u = get(id);
        if (nickname != null)
            u.setNickname(nickname);
        if (avatar != null)
            u.setAvatar(avatar);
        if (phone != null)
            u.setPhone(phone);
        return u;
    }
}
