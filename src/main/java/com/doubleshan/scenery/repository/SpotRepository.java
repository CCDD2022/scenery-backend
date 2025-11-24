package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    List<Spot> findByStatus(Integer status);

    @Query("select s from Spot s where s.status=1")
    List<Spot> listActive();
}
