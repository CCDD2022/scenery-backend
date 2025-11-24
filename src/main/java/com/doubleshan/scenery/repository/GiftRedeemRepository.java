package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.GiftRedeem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftRedeemRepository extends JpaRepository<GiftRedeem, String> {
    List<GiftRedeem> findByUserId(String userId);

    Optional<GiftRedeem> findByQrcode(String qrcode);
}
