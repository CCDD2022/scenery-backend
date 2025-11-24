package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.Checkin;
import com.doubleshan.scenery.model.LikeRecord;
import com.doubleshan.scenery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRecordRepository extends JpaRepository<LikeRecord, Long> {
    @Query("select l from LikeRecord l where l.user = :user and l.checkIn = :checkIn")
    Optional<LikeRecord> findByUserAndCheckIn(@Param("user") User user, @Param("checkIn") Checkin checkIn);

    @Query("select count(l) from LikeRecord l where l.checkIn = :checkIn")
    long countByCheckIn(@Param("checkIn") Checkin checkIn);
}
