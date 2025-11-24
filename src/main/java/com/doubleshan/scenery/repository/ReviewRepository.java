package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findByPoiIdOrderByCreatedAtDesc(String poiId);

    Page<Review> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
