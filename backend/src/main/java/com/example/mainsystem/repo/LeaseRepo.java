package com.example.mainsystem.repo;

import com.example.mainsystem.model.Lease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeaseRepo extends JpaRepository<Lease, UUID> {

    boolean existsByApartmentIdAndEndDateIsNull(UUID apartmentId);
}
