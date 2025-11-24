package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.Checkin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, String> {
    List<Checkin> findByUserIdOrderByCreatedAtDesc(String userId);

    List<Checkin> findByPoiIdOrderByCreatedAtDesc(String poiId);

    Page<Checkin> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
