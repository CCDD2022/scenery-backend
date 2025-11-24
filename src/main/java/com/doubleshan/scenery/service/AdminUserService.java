package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.User;
import com.doubleshan.scenery.repository.UserRepository;
import com.doubleshan.scenery.response.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminUserService {
    private final UserRepository userRepository;

    public AdminUserService(UserRepository r) {
        this.userRepository = r;
    }

    public List<User> list(String role, Boolean disabled) {
        return userRepository.findAll().stream()
                .filter(u -> role == null || u.getRole().equalsIgnoreCase(role))
                .filter(u -> disabled == null || u.isDisabled() == disabled)
                .collect(Collectors.toList());
    }

    public User updateRole(String id, String role) {
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("用户不存在"));
        u.setRole(role);
        return u;
    }

    public User disable(String id) {
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("用户不存在"));
        u.setDisabled(true);
        return u;
    }

    public User enable(String id) {
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("用户不存在"));
        u.setDisabled(false);
        return u;
    }

    public User updateDepartment(String id, String dept) {
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("用户不存在"));
        u.setDepartment(dept);
        return u;
    }
}