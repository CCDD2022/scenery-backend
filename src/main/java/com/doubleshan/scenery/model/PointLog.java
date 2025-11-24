package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Data
@Entity
@Table(name = "point_logs")
public class PointLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private PointLogType type;
    @Column(nullable = false)
    private Integer points;
    @Column(nullable = false)
    private Integer balance;
    private Long sourceId;
    @Column(length = 200)
    private String description;
    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}
