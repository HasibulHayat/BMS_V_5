package com.example.mainsystem.dto.apartment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentCreateRequest {
    @NotBlank(message = "Apartment name is required")
    private String name;

    private Integer floorNumber;
    private String sectorName;

    @NotNull(message = "Building Id is required")
    private UUID buildingId;

    private Integer totalRoom;
    private Integer totalBathroom;
    private Integer totalBedroom;
    private Integer totalBalconies;

    private Double areaSqFt;
    private Integer parkingSpotNumber;
    private String landphoneExtension;
    private String notes;
}
