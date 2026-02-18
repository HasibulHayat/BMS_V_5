package com.example.mainsystem.repo;

import com.example.mainsystem.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApartmentRepo extends JpaRepository<Apartment, UUID> {
    boolean existsByBuildingIdAndName(UUID buildingId, String name);

    List<Apartment> findByBuildingId(UUID buildingId);
}
