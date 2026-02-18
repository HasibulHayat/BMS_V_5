package com.example.mainsystem.dto.apartment;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApartmentResponse {
    private UUID id;

    private String name;
    private Integer floorNumber;
    private String sectorName;

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
