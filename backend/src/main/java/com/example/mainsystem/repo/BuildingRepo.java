package com.example.mainsystem.repo;

import com.example.mainsystem.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BuildingRepo extends JpaRepository<Building, UUID> {
    boolean existsByBuildingCode(String buildingCode);

    boolean existsByRegistrationNumber(String registrationNumber);
}

