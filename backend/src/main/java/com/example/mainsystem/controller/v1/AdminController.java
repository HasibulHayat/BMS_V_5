package com.example.mainsystem.controller.v1;

import com.example.mainsystem.dto.admin.AdminCreationRequest;
import com.example.mainsystem.dto.admin.AdminCreationResponse;
import com.example.mainsystem.dto.admin.AdminListResponse;
import com.example.mainsystem.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/superadmin/admins")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping()
    public ResponseEntity<AdminCreationResponse> createAdmin(@Valid @RequestBody AdminCreationRequest request) {
        AdminCreationResponse response = adminService.createAdmin(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AdminListResponse>> getAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

}
