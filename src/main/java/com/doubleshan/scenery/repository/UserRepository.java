package com.doubleshan.scenery.repository;

import com.doubleshan.scenery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByOpenId(String openId);
}
