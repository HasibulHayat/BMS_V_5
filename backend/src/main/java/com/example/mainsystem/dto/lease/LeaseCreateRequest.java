package com.example.mainsystem.dto.lease;

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
public class LeaseCreateRequest {
    @NotNull
    private UUID apartmentId;

    // Option 1
    private UUID existingUserProfileId;

    // Option 2
    private NewUserRequest newUser;

    // Option 3
    private Boolean ownerIsResident;

    @NotNull
    private BigDecimal rentAmount;

    @NotNull
    private LocalDate startDate;
}
