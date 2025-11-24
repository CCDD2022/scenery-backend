package com.doubleshan.scenery.controller.common;

import com.doubleshan.scenery.model.User;
import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.JwtUtil;
import com.doubleshan.scenery.service.AuthService;
import com.doubleshan.scenery.vo.UserVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    static class LoginReq {
        public String open_id;
        public String nickname;
        public String avatar;
    }

    @PostMapping("/login")
    public ApiResponse<Object> login(@RequestBody LoginReq req) {
        User u = authService.loginOrRegister(req.open_id, req.nickname, req.avatar);
        String token = JwtUtil.generateToken(u.getId(), u.getRole());
        return ApiResponse.ok(java.util.Map.of("token", token, "user", UserVO.from(u)));
    }
}
