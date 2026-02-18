package com.example.mainsystem.dto.building;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingCreateRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Building code is required")
    private String buildingCode;

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    private String buildingType;
    private String description;

    private String street;
    private String area;
    private String city;
    private String district;
    private String country;
    private String postalCode;
}
