package com.example.mainsystem.dto.lease;

import com.example.mainsystem.model.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewUserRequest {
    @NotBlank
    private String username;

    @NotBlank
    @Email(message = "Email is required")
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Gender gender;

    @NotBlank
    private String phone;
}
