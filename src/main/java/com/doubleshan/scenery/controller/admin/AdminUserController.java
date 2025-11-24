package com.doubleshan.scenery.controller.admin;

import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.RoleRequired;
import com.doubleshan.scenery.service.AdminUserService;
import com.doubleshan.scenery.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@RoleRequired({ "ADMIN" })
public class AdminUserController {
    private final AdminUserService service;

    public AdminUserController(AdminUserService s) {
        this.service = s;
    }

    @GetMapping
    public ApiResponse<List<UserVO>> list(@RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean disabled) {
        return ApiResponse.ok(service.list(role, disabled).stream().map(UserVO::from).toList());
    }

    static class RoleUpdate {
        public String role;
    }

    @PutMapping("/{id}/role")
    public ApiResponse<UserVO> updateRole(@PathVariable String id, @RequestBody RoleUpdate req) {
        return ApiResponse.ok(UserVO.from(service.updateRole(id, req.role)));
    }

    @PutMapping("/{id}/disable")
    public ApiResponse<UserVO> disable(@PathVariable String id) {
        return ApiResponse.ok(UserVO.from(service.disable(id)));
    }

    @PutMapping("/{id}/enable")
    public ApiResponse<UserVO> enable(@PathVariable String id) {
        return ApiResponse.ok(UserVO.from(service.enable(id)));
    }

    static class DeptUpdate {
        public String department;
    }

    @PutMapping("/{id}/department")
    public ApiResponse<UserVO> updateDept(@PathVariable String id, @RequestBody DeptUpdate req) {
        return ApiResponse.ok(UserVO.from(service.updateDepartment(id, req.department)));
    }
}