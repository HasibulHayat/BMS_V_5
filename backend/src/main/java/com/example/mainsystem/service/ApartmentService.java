package com.example.mainsystem.service;

import com.example.mainsystem.dto.apartment.ApartmentCreateRequest;
import com.example.mainsystem.dto.apartment.ApartmentResponse;
import com.example.mainsystem.dto.apartment.ApartmentUpdateRequest;
import com.example.mainsystem.exception.AuthException;
import com.example.mainsystem.model.Apartment;
import com.example.mainsystem.model.Building;
import com.example.mainsystem.repo.ApartmentRepo;
import com.example.mainsystem.repo.BuildingRepo;
import com.example.mainsystem.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ApartmentService {
    private final ApartmentRepo apartmentRepo;
    private final BuildingRepo buildingRepo;
    private final CurrentUser currentUser;

    public ApartmentService(
            ApartmentRepo apartmentRepo,
            BuildingRepo buildingRepo,
            CurrentUser currentUser
    ) {
        this.apartmentRepo = apartmentRepo;
        this.buildingRepo = buildingRepo;
        this.currentUser = currentUser;
    }

    public ApartmentResponse createApartment(ApartmentCreateRequest request) {
        if (!currentUser.hasAnyRole("SUPER_ADMIN", "ADMIN")) {
            throw new AuthException("You are not permitted to perform this operation");
        }

        Building building = buildingRepo.findById(request.getBuildingId())
                .orElseThrow(() -> new AuthException("Building not found"));

        if (apartmentRepo.existsByBuildingIdAndName(building.getId(), request.getName())) {
            throw new AuthException("Apartment already exists in this building");
        }

        Apartment apartment = Apartment.builder()
                .name(request.getName())
                .floorNumber(request.getFloorNumber())
                .sectorName(request.getSectorName())
                .building(building)
                .totalRoom(request.getTotalRoom())
                .totalBathroom(request.getTotalBathroom())
                .totalBedroom(request.getTotalBedroom())
                .totalBalconies(request.getTotalBalconies())
                .areaSqFt(request.getAreaSqFt())
                .parkingSpotNumber(request.getParkingSpotNumber())
                .landphoneExtension(request.getLandphoneExtension())
                .notes(request.getNotes())
                .build();

        apartmentRepo.save(apartment);

        return ApartmentResponse.builder()
                .id(apartment.getId())
                .name(apartment.getName())
                .floorNumber(apartment.getFloorNumber())
                .sectorName(apartment.getSectorName())
                .buildingId(apartment.getBuilding().getId())
                .totalRoom(apartment.getTotalRoom())
                .totalBedroom(apartment.getTotalBedroom())
                .totalBalconies(apartment.getTotalBalconies())
                .totalBathroom(apartment.getTotalBathroom())
                .areaSqFt(apartment.getAreaSqFt())
                .parkingSpotNumber(apartment.getParkingSpotNumber())
                .landphoneExtension(apartment.getLandphoneExtension())
                .notes(apartment.getNotes())
                .build();
    }

    public ApartmentResponse updateApartment(UUID id, ApartmentUpdateRequest request) {
        if (!currentUser.hasAnyRole("SUPER_ADMIN", "ADMIN")) {
            throw new AuthException("You are not permitted to perform this operation");
        }

        Apartment apartment = apartmentRepo.findById(id)
                .orElseThrow(() -> new AuthException("Apartment not found"));

        apartment.setName(request.getName());
        apartment.setFloorNumber(request.getFloorNumber());
        apartment.setSectorName(request.getSectorName());
        apartment.setTotalRoom(request.getTotalRoom());
        apartment.setTotalBathroom(request.getTotalBathroom());
        apartment.setTotalBedroom(request.getTotalBedroom());
        apartment.setTotalBalconies(request.getTotalBalconies());
        apartment.setAreaSqFt(request.getAreaSqFt());
        apartment.setParkingSpotNumber(request.getParkingSpotNumber());
        apartment.setLandphoneExtension(request.getLandphoneExtension());
        apartment.setNotes(request.getNotes());

        return ApartmentResponse.builder()
                .id(apartment.getId())
                .name(apartment.getName())
                .floorNumber(apartment.getFloorNumber())
                .sectorName(apartment.getSectorName())
                .buildingId(apartment.getBuilding().getId())
                .totalRoom(apartment.getTotalRoom())
                .totalBedroom(apartment.getTotalBedroom())
                .totalBalconies(apartment.getTotalBalconies())
                .totalBathroom(apartment.getTotalBathroom())
                .areaSqFt(apartment.getAreaSqFt())
                .parkingSpotNumber(apartment.getParkingSpotNumber())
                .landphoneExtension(apartment.getLandphoneExtension())
                .notes(apartment.getNotes())
                .build();
    }

    public ApartmentResponse getApartment(UUID id) {
        if (!currentUser.hasAnyRole("SUPER_ADMIN", "ADMIN")) {
            throw new AuthException("You are not permitted to perform this operation");
        }

        Apartment apartment = apartmentRepo.findById(id)
                .orElseThrow(() -> new AuthException("Apartment not found"));

        return ApartmentResponse.builder()
                .id(apartment.getId())
                .name(apartment.getName())
                .floorNumber(apartment.getFloorNumber())
                .sectorName(apartment.getSectorName())
                .buildingId(apartment.getBuilding().getId())
                .totalRoom(apartment.getTotalRoom())
                .totalBedroom(apartment.getTotalBedroom())
                .totalBalconies(apartment.getTotalBalconies())
                .totalBathroom(apartment.getTotalBathroom())
                .areaSqFt(apartment.getAreaSqFt())
                .parkingSpotNumber(apartment.getParkingSpotNumber())
                .landphoneExtension(apartment.getLandphoneExtension())
                .notes(apartment.getNotes())
                .build();
    }

    public List<ApartmentResponse> getAllApartments(UUID id) {
        if (!currentUser.hasAnyRole("SUPER_ADMIN", "ADMIN")) {
            throw new AuthException("You are not permitted to perform this operation");
        }

        Building building = buildingRepo.findById(id)
                .orElseThrow(() -> new AuthException("Building not found"));

        List<Apartment> apartments = apartmentRepo.findByBuildingId(id);
        List<ApartmentResponse> responses = new ArrayList<>();

        for (Apartment apartment : apartments) {
            ApartmentResponse response = ApartmentResponse.builder()
                    .id(apartment.getId())
                    .name(apartment.getName())
                    .floorNumber(apartment.getFloorNumber())
                    .sectorName(apartment.getSectorName())
                    .buildingId(apartment.getBuilding().getId())
                    .totalRoom(apartment.getTotalRoom())
                    .totalBedroom(apartment.getTotalBedroom())
                    .totalBalconies(apartment.getTotalBalconies())
                    .totalBathroom(apartment.getTotalBathroom())
                    .areaSqFt(apartment.getAreaSqFt())
                    .parkingSpotNumber(apartment.getParkingSpotNumber())
                    .landphoneExtension(apartment.getLandphoneExtension())
                    .notes(apartment.getNotes())
                    .build();

            responses.add(response);
        }

        return responses;
    }
}
