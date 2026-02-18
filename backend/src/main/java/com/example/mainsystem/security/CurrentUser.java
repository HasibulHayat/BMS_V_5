package com.example.mainsystem.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CurrentUser {
    private Authentication authentication() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user");
        }

        return auth;
    }

    public UUID id() {
        Object principal = authentication().getPrincipal();

        if (principal instanceof UUID uuid) {
            return uuid;
        }

        throw new IllegalStateException("Unexpected principal type");
    }

    public Set<String> roles() {
        return authentication().getAuthorities()
                .stream()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toSet());
    }

    public boolean hasRole(String role) {
        return roles().contains(role);
    }

    public boolean hasAnyRole(String... roles) {
        Set<String> userRoles = roles();
        for (String role : roles) {
            if (userRoles.contains(role)) {
                return true;
            }
        }
        return false;
    }
}
