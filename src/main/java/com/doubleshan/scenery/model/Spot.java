package com.doubleshan.scenery.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "spots")
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal lat;
    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal lng;
    @Lob
    private String description;
    @Column(length = 500)
    private String image;
    @Column(nullable = false)
    private Integer status = 1;
    @Column(nullable = false)
    private Instant createdAt = Instant.now();
    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
