package com.example.mainsystem.dto.apartment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentUpdateRequest {
    @NotBlank(message = "Apartment name is required")
    private String name;

    private Integer floorNumber;
    private String sectorName;

    private Integer totalRoom;
    private Integer totalBathroom;
    private Integer totalBedroom;
    private Integer totalBalconies;

    private Double areaSqFt;
    private Integer parkingSpotNumber;
    private String landphoneExtension;
    private String notes;
}
