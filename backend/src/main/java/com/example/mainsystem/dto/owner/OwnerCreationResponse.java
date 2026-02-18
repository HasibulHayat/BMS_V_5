package com.example.mainsystem.dto.owner;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerCreationResponse {
    private UUID profileId;

    private String username;

    private String email;

    // simple for now
}
