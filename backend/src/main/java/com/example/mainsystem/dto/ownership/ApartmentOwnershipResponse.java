package com.example.mainsystem.dto.ownership;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentOwnershipResponse {

    private UUID id;
    private UUID apartmentId;
    private UUID ownerProfileId;

    private BigDecimal ownershipPercentage;
    private LocalDate ownershipStartDate;
    private LocalDate ownershipEndDate;

    private String notes;
}
