package com.example.mainsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Building extends BaseModel {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String buildingCode;

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

    @Column(unique = true, nullable = false)
    private String registrationNumber;

    private Double landAreaSqFt;
    private Double floorAreaSqFt;
    private Double unitAreaSqFt;

    private String developerName;
    private String ownerAssociationName;

    @Column(length = 1000)
    private String notes;
}