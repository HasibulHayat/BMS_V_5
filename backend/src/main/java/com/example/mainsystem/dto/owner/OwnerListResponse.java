package com.example.mainsystem.dto.owner;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerListResponse {
    private UUID id;

    private String firstName;
    private String lastName;

    private String phone;
    private String email;

    private Boolean isActive;

    private List<String> buildings;
}
