package com.example.mainsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile extends BaseModel {
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String middleName;

    @Column(nullable = false, unique = true)
    private String phonePrimary;
    private String phoneSecondary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    private LocalDate birthDate;
    private String permanentAddress;
    private String occupation;

    private String nationalId;
    private String passportId;
    private String drivingLicenseId;

    private String emergencyContactName;
    private String emergencyContactPhone;

    private Boolean isMarried;

    @Column(nullable = false)
    private Boolean isActive;

    @Column(length = 1000)
    private String notes;
}
