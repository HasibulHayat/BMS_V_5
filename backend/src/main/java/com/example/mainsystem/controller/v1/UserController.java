package com.example.mainsystem.controller.v1;

import com.example.mainsystem.dto.me.UserMeResponse;
import com.example.mainsystem.dto.me.UserProfileUpdateRequest;
import com.example.mainsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserMeResponse> me() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMyProfile(
            @Valid @RequestBody UserProfileUpdateRequest request
    ) {
        userService.updateMyProfile(request);
        return ResponseEntity.noContent().build();
    }
}
