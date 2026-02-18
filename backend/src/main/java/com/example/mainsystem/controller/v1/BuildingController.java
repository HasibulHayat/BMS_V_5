package com.example.mainsystem.controller.v1;

import com.example.mainsystem.dto.building.BuildingCreateRequest;
import com.example.mainsystem.dto.building.BuildingResponse;
import com.example.mainsystem.dto.building.BuildingUpdateRequest;
import com.example.mainsystem.service.BuildingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/buildings")
public class BuildingController {
    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @PostMapping
    public ResponseEntity<BuildingResponse> createBuilding(@Valid @RequestBody BuildingCreateRequest request) {
        BuildingResponse response = buildingService.createBuilding(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildingResponse> updateBuilding(@PathVariable UUID id, @Valid @RequestBody BuildingUpdateRequest request) {
        BuildingResponse response = buildingService.updateBuilding(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildingResponse> getBuilding(@PathVariable UUID id) {
        BuildingResponse response = buildingService.getBuilding(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BuildingResponse>> getAllBuildings() {
        return ResponseEntity.ok(buildingService.getAllBuildings());
    }

}
