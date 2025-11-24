package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Data
@Entity
@Table(name = "exchange_records")
public class ExchangeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prize_id", nullable = false)
    private Prize prize;
    @Column(nullable = false, length = 8, unique = true)
    private String exchangeCode;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ExchangeStatus status = ExchangeStatus.pending;
    private Instant usedAt;
    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}
