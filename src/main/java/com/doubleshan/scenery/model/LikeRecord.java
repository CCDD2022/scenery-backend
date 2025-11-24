package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Data
@Entity
@Table(name = "likes", uniqueConstraints = @UniqueConstraint(name = "uk_user_checkin", columnNames = { "user_id",
        "check_in_id" }))
public class LikeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "check_in_id", nullable = false)
    private Checkin checkIn;
    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}
