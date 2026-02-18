package com.example.mainsystem.dto.building;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingResponse {
    private UUID id;
    private String name;
    private String buildingCode;
    private String registrationNumber;
    private String city;
    private String country;
}
