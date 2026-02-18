package com.example.mainsystem.dto.ownership;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentOwnershipCreateRequest {

    @NotNull(message = "Apartment ID is required")
    private UUID apartmentId;

    @NotNull(message = "Owner profile ID is required")
    private UUID ownerProfileId;

    @NotNull(message = "Ownership percentage is required")
    @DecimalMin(value = "0.01", message = "Ownership must be greater than 0")
    @DecimalMax(value = "100.00", message = "Ownership cannot exceed 100")
    private BigDecimal ownershipPercentage;

    @NotNull(message = "Ownership start date is required")
    private LocalDate ownershipStartDate;

    private String notes;
}

