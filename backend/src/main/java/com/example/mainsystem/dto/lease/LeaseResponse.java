package com.example.mainsystem.dto.lease;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaseResponse {

    private UUID id;
    private UUID apartmentId;
    private UUID residentProfileId;

    private BigDecimal rentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
}
