package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.POI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POIRepository extends JpaRepository<POI, String> {
}
