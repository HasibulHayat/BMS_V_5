package com.example.mainsystem.dto.me;

import com.example.mainsystem.model.BloodGroup;
import com.example.mainsystem.model.Gender;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileUpdateRequest {
    @NotBlank
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String middleName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Secondary phone must be a valid international number"
    )
    private String phoneSecondary;

    @NotNull
    private Gender gender;

    private BloodGroup bloodGroup;

    private LocalDate birthDate;

    @Size(max = 255)
    private String permanentAddress;

    @Size(max = 100)
    private String occupation;

    @Size(max = 50)
    private String nationalId;

    @Size(max = 50)
    private String passportId;

    @Size(max = 50)
    private String drivingLicenseId;

    @Size(max = 100)
    private String emergencyContactName;

    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Emergency contact phone must be valid"
    )
    private String emergencyContactPhone;

    private Boolean isMarried;

    @Size(max = 1000)
    private String notes;
}
