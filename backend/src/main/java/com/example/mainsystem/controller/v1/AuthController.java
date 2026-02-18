package com.example.mainsystem.controller.v1;

import com.example.mainsystem.dto.login.LoginRequest;
import com.example.mainsystem.dto.login.LoginResponse;
import com.example.mainsystem.dto.me.MeResponse;
import com.example.mainsystem.security.CurrentUser;
import com.example.mainsystem.service.AuthService;
import com.example.mainsystem.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final CurrentUser currentUser;

    public AuthController(AuthService authService, CurrentUser currentUser) {
        this.authService = authService;
        this.currentUser = currentUser;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public MeResponse me() {
        return MeResponse.builder()
                .id(currentUser.id())
                .roles(currentUser.roles())
                .build();
    }
}
