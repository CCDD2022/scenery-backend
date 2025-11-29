package com.doubleshan.scenery.controller;

import com.doubleshan.scenery.common.api.ApiResponse;
import com.doubleshan.scenery.model.User;
import com.doubleshan.scenery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class RankingController {
    private final UserRepository userRepository;

    @GetMapping("/ranking")
    public ApiResponse<Page<User>> ranking(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<User> p = userRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "totalPoints")));
        return ApiResponse.ok(p);
    }
}
