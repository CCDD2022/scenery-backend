package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftRepository extends JpaRepository<Gift, String> {
}
