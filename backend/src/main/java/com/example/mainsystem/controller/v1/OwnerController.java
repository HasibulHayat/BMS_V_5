package com.example.mainsystem.controller.v1;

import com.example.mainsystem.dto.owner.OwnerCreationRequest;
import com.example.mainsystem.dto.owner.OwnerCreationResponse;
import com.example.mainsystem.dto.owner.OwnerListResponse;
import com.example.mainsystem.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/owners")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    public ResponseEntity<OwnerCreationResponse> createOwner(@Valid @RequestBody OwnerCreationRequest request) {
        OwnerCreationResponse response = ownerService.createOwner(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OwnerListResponse>> getOwners() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

}
