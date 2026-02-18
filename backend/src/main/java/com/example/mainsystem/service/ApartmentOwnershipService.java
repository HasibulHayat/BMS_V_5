package com.example.mainsystem.service;

import com.example.mainsystem.dto.ownership.ApartmentOwnershipCreateRequest;
import com.example.mainsystem.dto.ownership.ApartmentOwnershipResponse;
import com.example.mainsystem.exception.AuthException;
import com.example.mainsystem.model.Apartment;
import com.example.mainsystem.model.ApartmentOwnership;
import com.example.mainsystem.model.UserProfile;
import com.example.mainsystem.repo.ApartmentOwnershipRepo;
import com.example.mainsystem.repo.ApartmentRepo;
import com.example.mainsystem.repo.UserProfileRepo;
import com.example.mainsystem.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
public class ApartmentOwnershipService {

    private final ApartmentOwnershipRepo ownershipRepo;
    private final ApartmentRepo apartmentRepo;
    private final UserProfileRepo userProfileRepo;
    private final CurrentUser currentUser;

    public ApartmentOwnershipService(
            ApartmentOwnershipRepo ownershipRepo,
            ApartmentRepo apartmentRepo,
            UserProfileRepo userProfileRepo,
            CurrentUser currentUser
    ) {
        this.ownershipRepo = ownershipRepo;
        this.apartmentRepo = apartmentRepo;
        this.userProfileRepo = userProfileRepo;
        this.currentUser = currentUser;
    }

    public ApartmentOwnershipResponse assignOwnership(
            ApartmentOwnershipCreateRequest request
    ) {
        if (!currentUser.hasAnyRole("SUPER_ADMIN", "ADMIN")) {
            throw new AuthException("You are not allowed to assign ownership");
        }

        Apartment apartment = apartmentRepo.findById(request.getApartmentId())
                .orElseThrow(() -> new AuthException("Apartment not found"));

        UserProfile owner = userProfileRepo.findById(request.getOwnerProfileId())
                .orElseThrow(() -> new AuthException("Owner profile not found"));

        if (ownershipRepo.existsByApartmentIdAndUserProfileIdAndOwnershipEndDateIsNull(
                apartment.getId(),
                owner.getId()
        )) {
            throw new AuthException("Owner already has active ownership");
        }

        BigDecimal totalOwnership =
                ownershipRepo.totalActiveOwnership(apartment.getId());

        BigDecimal newTotal =
                totalOwnership.add(request.getOwnershipPercentage());

        if (newTotal.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new AuthException("Total ownership exceeds 100%");
        }

        ApartmentOwnership ownership = ApartmentOwnership.builder()
                .apartment(apartment)
                .userProfile(owner)
                .ownershipPercentage(request.getOwnershipPercentage())
                .ownershipStartDate(request.getOwnershipStartDate())
                .notes(request.getNotes())
                .build();

        ownershipRepo.save(ownership);

        return ApartmentOwnershipResponse.builder()
                .id(ownership.getId())
                .apartmentId(apartment.getId())
                .ownerProfileId(owner.getId())
                .ownershipPercentage(ownership.getOwnershipPercentage())
                .ownershipStartDate(ownership.getOwnershipStartDate())
                .ownershipEndDate(ownership.getOwnershipEndDate())
                .notes(ownership.getNotes())
                .build();
    }
}

