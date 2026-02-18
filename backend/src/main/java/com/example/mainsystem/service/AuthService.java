package com.example.mainsystem.service;

import com.example.mainsystem.dto.login.LoginRequest;
import com.example.mainsystem.dto.login.LoginResponse;
import com.example.mainsystem.exception.AuthException;
import com.example.mainsystem.model.UserAuth;
import com.example.mainsystem.repo.UserAuthRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@Transactional
public class AuthService {
    private final UserAuthRepo userAuthRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserAuthRepo userAuthRepo, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userAuthRepo = userAuthRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        UserAuth user = userAuthRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("Email not found"));

        if (user.getIsLocked() == true) {
            throw new AuthException("User has been locked");
        }

        if (user.getIsActive() == false) {
            throw new AuthException("User has been disabled");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new AuthException("Passwords don't match");
        }

        Set<String> roles = userAuthRepo.findActiveRoleNamesByEmail(request.getEmail());

        String token = jwtService.generateToken(user.getId(), roles);
        long expiration = 120000;

        return LoginResponse.builder()
                .accessToken(token)
                .expiration(expiration)
                .build();
    }
}
