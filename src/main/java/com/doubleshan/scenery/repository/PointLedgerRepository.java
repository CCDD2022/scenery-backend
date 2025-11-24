package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.PointLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointLedgerRepository extends JpaRepository<PointLedger, String> {
    List<PointLedger> findByUserIdOrderByCreatedAtDesc(String userId);

    List<PointLedger> findAllByOrderByCreatedAtDesc();
}
