package com.doubleshan.scenery.service;

import com.doubleshan.scenery.common.exception.BizException;
import com.doubleshan.scenery.dto.AuthDtos;
import com.doubleshan.scenery.model.Admin;
import com.doubleshan.scenery.model.User;
import com.doubleshan.scenery.repository.AdminRepository;
import com.doubleshan.scenery.repository.UserRepository;
import com.doubleshan.scenery.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        return buildUserDetails(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 先查普通用户，再查管理员
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return buildUserDetails(user);
        }
        Admin admin = adminRepository.findByUsername(username).orElse(null);
        if (admin != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(admin.getUsername())
                    .password(admin.getPasswordHash())
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .build();
        }
        throw new UsernameNotFoundException("账号不存在");
    }

    private UserDetails buildUserDetails(User user) {
        Collection<? extends GrantedAuthority> auth = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(auth)
                .build();
    }

    @Transactional
    public AuthDtos.RegisterResp register(AuthDtos.RegisterReq req) {
        userRepository.findByUsername(req.getUsername()).ifPresent(u -> {
            throw new BizException("用户名已存在");
        });
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setNickName(req.getNickName());
        userRepository.save(user);
        AuthDtos.RegisterResp resp = new AuthDtos.RegisterResp();
        resp.setUserId(user.getId());
        resp.setToken(jwtUtil.createToken(user.getId(), user.getUsername()));
        return resp;
    }

    public AuthDtos.LoginResp login(AuthDtos.LoginReq req) {
        UserDetails ud = loadUserByUsername(req.getUsername());
        if (!passwordEncoder.matches(req.getPassword(), ud.getPassword())) {
            throw new BizException("密码错误");
        }
        User user = userRepository.findByUsername(req.getUsername()).orElse(null);
        AuthDtos.LoginResp resp = new AuthDtos.LoginResp();
        if (user != null) {
            resp.setUserId(user.getId());
            resp.setNickName(user.getNickName());
            resp.setAvatar(user.getAvatarUrl());
        } else {
            // 管理员只返回ID=-1
            resp.setUserId(-1L);
            resp.setNickName("admin");
            resp.setAvatar("");
        }
        resp.setToken(jwtUtil.createToken(resp.getUserId(), req.getUsername()));
        return resp;
    }

    // 管理员登录（仅账号密码，不返回用户资料）
    public String adminLogin(String username, String password) {
        Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new BizException("管理员不存在"));
        if (!passwordEncoder.matches(password, admin.getPasswordHash())) {
            throw new BizException("密码错误");
        }
        // 管理员用 -1 作为 subject（简单区分），并标记角色
        return jwtUtil.createToken(-1L, username, "ROLE_ADMIN");
    }

    @Transactional
    public void bindPhone(Long userId, String phone) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BizException("用户不存在"));
        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            throw new BizException("已绑定手机号");
        }
        user.setPhone(phone);
        userRepository.save(user);
    }
}
