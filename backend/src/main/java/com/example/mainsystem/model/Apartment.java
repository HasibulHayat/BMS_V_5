package com.example.mainsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"building_id", "name"}
        )
)
public class Apartment extends BaseModel {
    @Column(nullable = false)
    private String name;

    private Integer floorNumber;
    private String sectorName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "building_id",nullable = false)
    private Building building;

    private Integer totalRoom;
    private Integer totalBathroom;
    private Integer totalBedroom;
    private Integer totalBalconies;

    private Double areaSqFt;

    private Integer parkingSpotNumber;

    private String landphoneExtension;

    @Column(length = 1000)
    private String notes;
}
