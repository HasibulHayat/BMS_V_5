package com.example.mainsystem.dto.admin;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AdminListResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Boolean isActive;
}
