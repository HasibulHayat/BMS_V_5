package com.example.mainsystem.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SpringSecurityAuditorAware
        implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty(); // seed / system / background
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof UUID userAuthId) {
            return Optional.of(userAuthId);
        }

        return Optional.empty();
    }
}
