package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrizeRepository extends JpaRepository<Prize, Long> {
    List<Prize> findByStatus(Integer status);
}
