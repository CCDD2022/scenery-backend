package com.doubleshan.scenery.controller.visitor;

import com.doubleshan.scenery.model.Checkin;
import com.doubleshan.scenery.model.POI;
import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.SecurityContext;
import com.doubleshan.scenery.vo.UserVO;
import com.doubleshan.scenery.vo.CheckinVO;
import com.doubleshan.scenery.vo.POIVO;
import com.doubleshan.scenery.service.CheckinService;
import com.doubleshan.scenery.service.POIService;
import com.doubleshan.scenery.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final CheckinService checkinService;
    private final POIService poiService;

    public UserController(UserService userService, CheckinService checkinService, POIService poiService) {
        this.userService = userService;
        this.checkinService = checkinService;
        this.poiService = poiService;
    }

    static class UpdateReq {
        public String nickname;
        public String avatar;
        public String phone;
    }

    @PatchMapping("/me")
    public ApiResponse<UserVO> updateMe(@RequestBody UpdateReq req) {
        String userId = SecurityContext.getUserId();
        return ApiResponse.ok(UserVO.from(userService.update(userId, req.nickname, req.avatar, req.phone)));
    }

    @GetMapping("/me/checkins")
    public ApiResponse<List<CheckinVO>> myCheckins() {
        String userId = SecurityContext.getUserId();
        return ApiResponse.ok(checkinService.userCheckins(userId).stream().map(CheckinVO::from).toList());
    }

    @GetMapping("/checkins/unvisited")
    public ApiResponse<List<POIVO>> unvisited() {
        String userId = SecurityContext.getUserId();
        Set<String> visited = checkinService.userCheckins(userId).stream().map(Checkin::getPoiId)
                .collect(Collectors.toSet());
        List<POI> all = poiService.all();
        return ApiResponse.ok(all.stream().filter(p -> !visited.contains(p.getId())).map(POIVO::from).toList());
    }
}
