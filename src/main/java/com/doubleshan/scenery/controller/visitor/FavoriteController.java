package com.doubleshan.scenery.controller.visitor;

import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.security.SecurityContext;
import com.doubleshan.scenery.service.FavoriteService;
import com.doubleshan.scenery.vo.FavoriteVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    static class AddReq {
        public String poi_id;
    }

    @GetMapping
    public ApiResponse<List<FavoriteVO>> list() {
        String userId = SecurityContext.getUserId();
        List<FavoriteVO> list = favoriteService.list(userId).stream().map(FavoriteVO::from).toList();
        return ApiResponse.ok(list);
    }

    @PostMapping
    public ApiResponse<FavoriteVO> add(@RequestBody AddReq req) {
        String userId = SecurityContext.getUserId();
        return ApiResponse.ok(FavoriteVO.from(favoriteService.add(userId, req.poi_id)));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        favoriteService.remove(id);
        return ApiResponse.ok(null);
    }
}
