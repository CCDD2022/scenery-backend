package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.PointLog;
import com.doubleshan.scenery.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLogRepository extends JpaRepository<PointLog, Long> {
    Page<PointLog> findByUserOrderByIdDesc(User user, Pageable pageable);
}
