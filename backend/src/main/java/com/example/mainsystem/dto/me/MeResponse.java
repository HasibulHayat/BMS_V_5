package com.example.mainsystem.dto.me;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class MeResponse {
    private UUID id;
    private Set<String> roles;
}