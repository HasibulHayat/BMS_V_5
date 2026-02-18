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
public class ApartmentOwnership extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal ownershipPercentage;

    @Column(nullable = false)
    private LocalDate ownershipStartDate;

    private LocalDate ownershipEndDate;

    @Column(length = 1000)
    private String notes;
}