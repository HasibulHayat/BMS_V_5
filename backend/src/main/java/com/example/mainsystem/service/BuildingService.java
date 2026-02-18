package com.example.mainsystem.service;

import com.example.mainsystem.dto.building.BuildingCreateRequest;
import com.example.mainsystem.dto.building.BuildingResponse;
import com.example.mainsystem.dto.building.BuildingUpdateRequest;
import com.example.mainsystem.exception.AuthException;
import com.example.mainsystem.model.Building;
import com.example.mainsystem.repo.BuildingRepo;
import com.example.mainsystem.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BuildingService {
    private final BuildingRepo buildingRepo;
    private final CurrentUser currentUser;

    public BuildingService(BuildingRepo buildingRepo, CurrentUser currentUser) {
        this.buildingRepo = buildingRepo;
        this.currentUser = currentUser;
    }

    public BuildingResponse createBuilding(BuildingCreateRequest request) {
        if (!currentUser.hasAnyRole("SUPER_ADMIN", "ADMIN")) {
            throw new AuthException("You are not allowed to perform this action");
        }

        if (buildingRepo.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new AuthException("This building already exists");
        }

        if (buildingRepo.existsByBuildingCode(request.getBuildingCode())) {
            throw new AuthException("This building already exists");
        }

        Building building = Building.builder()
                .name(request.getName())
                .registrationNumber(request.getRegistrationNumber())
                .buildingCode(request.getBuildingCode())
                .buildingType(request.getBuildingType())
                .description(request.getDescription())
                .street(request.getStreet())
                .area(request.getArea())
                .city(request.getCity())
                .district(request.getDistrict())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .build();

        buildingRepo.save(building);

        return BuildingResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .buildingCode(building.getBuildingCode())
                .registrationNumber(building.getRegistrationNumber())
                .city(building.getCity())
                .country(building.getCountry())
                .build();
    }

    public BuildingResponse updateBuilding(UUID id, BuildingUpdateRequest request) {
        if (!currentUser.hasAnyRole("SUPER_ADMIN", "ADMIN")) {
            throw new AuthException("You are not allowed to perform this action");
        }

        Building building = buildingRepo.findById(id)
                .orElseThrow(() -> new AuthException("Building not found"));

        building.setName(request.getName());
        building.setBuildingType(request.getBuildingType());
        building.setDescription(request.getDescription());
        building.setStreet(request.getStreet());
        building.setArea(request.getArea());
        building.setCity(request.getCity());
        building.setDistrict(request.getDistrict());
        building.setCountry(request.getCountry());
        building.setPostalCode(request.getPostalCode());

        return BuildingResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .buildingCode(building.getBuildingCode())
                .registrationNumber(building.getRegistrationNumber())
                .city(building.getCity())
                .country(building.getCountry())
                .build();
    }

    public BuildingResponse getBuilding(UUID id) {
        if (!currentUser.hasAnyRole("SUPER_ADMIN", "ADMIN")) {
            throw new AuthException("You are not allowed to perform this action");
        }

        Building building = buildingRepo.findById(id)
                .orElseThrow(() -> new AuthException("Building not found"));

        return BuildingResponse.builder()
                .id(building.getId())
                .name(building.getName())
                .buildingCode(building.getBuildingCode())
                .registrationNumber(building.getRegistrationNumber())
                .city(building.getCity())
                .country(building.getCountry())
                .build();
    }

    public List<BuildingResponse> getAllBuildings() {
        if (!currentUser.hasAnyRole("SUPER_ADMIN", "ADMIN")) {
            throw new AuthException("You are not allowed to perform this action");
        }

        List<Building> buildings = buildingRepo.findAll();
        List<BuildingResponse> responses = new ArrayList<>();

        for (Building building : buildings) {
            BuildingResponse response = BuildingResponse.builder()
                    .id(building.getId())
                    .name(building.getName())
                    .buildingCode(building.getBuildingCode())
                    .registrationNumber(building.getRegistrationNumber())
                    .city(building.getCity())
                    .country(building.getCountry())
                    .build();

            responses.add(response);
        }

        return responses;
    }
}
