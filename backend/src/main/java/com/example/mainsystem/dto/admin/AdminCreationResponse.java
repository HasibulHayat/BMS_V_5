package com.example.mainsystem.dto.admin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCreationResponse {
    private String username;

    private String email;

    // simple for now
}
