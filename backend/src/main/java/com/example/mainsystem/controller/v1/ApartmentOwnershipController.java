package com.example.mainsystem.controller.v1;

import com.example.mainsystem.dto.ownership.ApartmentOwnershipCreateRequest;
import com.example.mainsystem.dto.ownership.ApartmentOwnershipResponse;
import com.example.mainsystem.service.ApartmentOwnershipService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/apartment-ownerships")
public class ApartmentOwnershipController {

    private final ApartmentOwnershipService ownershipService;

    public ApartmentOwnershipController(
            ApartmentOwnershipService ownershipService
    ) {
        this.ownershipService = ownershipService;
    }

    @PostMapping
    public ResponseEntity<ApartmentOwnershipResponse> assignOwnership(
            @Valid @RequestBody ApartmentOwnershipCreateRequest request
    ) {
        return ResponseEntity.ok(
                ownershipService.assignOwnership(request)
        );
    }
}
