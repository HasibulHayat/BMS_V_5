package com.example.mainsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lease extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resident_profile_id", nullable = false)
    private UserProfile residentProfile;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal rentAmount;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;
}