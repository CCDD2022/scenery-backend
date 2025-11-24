package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "check_ins")
public class Checkin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id", nullable = false)
    private Spot spot;
    @Column(nullable = false, length = 200)
    private String photoFileId;
    @Column(length = 500)
    private String comment;
    @Column(nullable = false)
    private Integer pointsEarned = 0;
    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal checkLat;
    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal checkLng;
    @Column(precision = 8, scale = 2)
    private BigDecimal distance;
    @Column(nullable = false)
    private Integer status = 1;
    @Column(nullable = false)
    private Instant createdAt = Instant.now();
    // Clarified CheckIn class for better understanding
}
