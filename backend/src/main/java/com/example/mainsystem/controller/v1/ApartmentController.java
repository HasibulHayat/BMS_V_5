package com.example.mainsystem.controller.v1;

import com.example.mainsystem.dto.apartment.ApartmentCreateRequest;
import com.example.mainsystem.dto.apartment.ApartmentResponse;
import com.example.mainsystem.dto.apartment.ApartmentUpdateRequest;
import com.example.mainsystem.service.ApartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/apartments")
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @PostMapping
    public ResponseEntity<ApartmentResponse> createApartment(@Valid @RequestBody ApartmentCreateRequest request) {
        return ResponseEntity.ok(apartmentService.createApartment(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentResponse> updateApartment(@PathVariable UUID id, @Valid @RequestBody ApartmentUpdateRequest request) {
        return ResponseEntity.ok(apartmentService.updateApartment(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentResponse> getApartment(@PathVariable UUID id) {
        return ResponseEntity.ok(apartmentService.getApartment(id));
    }

    @GetMapping("/building/{id}")
    public ResponseEntity<List<ApartmentResponse>> getAllApartments(@PathVariable UUID id) {
        return ResponseEntity.ok(apartmentService.getAllApartments(id));
    }
}

