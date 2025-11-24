package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.ExchangeRecord;
import com.doubleshan.scenery.model.ExchangeStatus;
import com.doubleshan.scenery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRecordRepository extends JpaRepository<ExchangeRecord, Long> {
    Optional<ExchangeRecord> findByExchangeCode(String code);

    long countByUserAndStatus(User user, ExchangeStatus status);
}
