package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.Review;
import com.doubleshan.scenery.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review create(String userId, String poiId, String checkinId, int stars, String content) {
        if (stars < 1 || stars > 5)
            throw new IllegalArgumentException("星级范围1-5");
        Review r = new Review(userId, poiId, checkinId, stars, content);
        reviewRepository.save(r);
        return r;
    }

    public List<Review> listByPoi(String poiId) {
        return reviewRepository.findByPoi(poiId);
    }

    public List<Review> dynamics(int limit) {
        return reviewRepository.recent(limit);
    }
}
