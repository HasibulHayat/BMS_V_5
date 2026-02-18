package com.example.mainsystem.dto.me;

import com.example.mainsystem.model.BloodGroup;
import com.example.mainsystem.model.Gender;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Getter
@Builder
public class UserMeResponse {
    private UUID id;

    private String username;
    private String email;
    private String phone;

    private String firstName;
    private String lastName;
    private String middleName;

    private String phoneSecondary;

    private Gender gender;

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

    private Set<String> roles;
    private Boolean isActive;

    private String notes;
}

