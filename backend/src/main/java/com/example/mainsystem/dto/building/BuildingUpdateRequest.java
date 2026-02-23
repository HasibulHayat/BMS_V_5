package com.example.mainsystem.dto.building;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingUpdateRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String buildingType;
    private String description;

    private String fullAddress;

    private String street;
    private String area;
    private String city;
    private String district;
    private String country;
    private String postalCode;

    private Double latitude;
    private Double longitude;

    private Integer totalFloor;
    private Integer basementFloor;
    private Integer totalUnit;

    private LocalDate constructionStart;
    private LocalDate constructionEnd;

    private Integer elevatorCount;
    private Boolean hasGenerator;
    private Integer totalParking;
    private Boolean hasGuard;
    private Boolean hasCCTV;
    private String waterSource;

    private Double landAreaSqFt;
    private Double floorAreaSqFt;
    private Double unitAreaSqFt;

    private String developerName;
    private String ownerAssociationName;

    private String notes;
}
