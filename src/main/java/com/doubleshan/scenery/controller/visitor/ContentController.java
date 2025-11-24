package com.doubleshan.scenery.controller.visitor;

import com.doubleshan.scenery.response.ApiResponse;
import com.doubleshan.scenery.service.ContentService;
import com.doubleshan.scenery.vo.ActivityVO;
import com.doubleshan.scenery.vo.HotlineVO;
import com.doubleshan.scenery.vo.IntroVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ContentController {
    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/intro")
    public ApiResponse<IntroVO> intro() {
        return ApiResponse.ok(IntroVO.from(contentService.getIntro()));
    }

    @GetMapping("/activity")
    public ApiResponse<ActivityVO> activity() {
        return ApiResponse.ok(ActivityVO.from(contentService.getActivity()));
    }

    @GetMapping("/hotline")
    public ApiResponse<HotlineVO> hotline() {
        return ApiResponse.ok(HotlineVO.from(contentService.getHotline()));
    }
}
