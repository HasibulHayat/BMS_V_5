package com.example.mainsystem.controller.v1;

import com.example.mainsystem.dto.lease.LeaseCreateRequest;
import com.example.mainsystem.dto.lease.LeaseResponse;
import com.example.mainsystem.service.LeaseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/leases")
public class LeaseController {

    private final LeaseService leaseService;

    public LeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @PostMapping
    public ResponseEntity<LeaseResponse> createLease(
            @Valid @RequestBody LeaseCreateRequest request
    ) {
        return ResponseEntity.ok(leaseService.createLease(request));
    }
}
