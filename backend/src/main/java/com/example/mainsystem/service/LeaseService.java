package com.example.mainsystem.service;

import com.example.mainsystem.dto.lease.LeaseCreateRequest;
import com.example.mainsystem.dto.lease.LeaseResponse;
import com.example.mainsystem.dto.lease.NewUserRequest;
import com.example.mainsystem.exception.AuthException;
import com.example.mainsystem.model.*;
import com.example.mainsystem.repo.*;
import com.example.mainsystem.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LeaseService {

    private final LeaseRepo leaseRepo;
    private final ApartmentRepo apartmentRepo;
    private final ApartmentOwnershipRepo ownershipRepo;
    private final UserProfileRepo userProfileRepo;
    private final UserAuthRepo userAuthRepo;
    private final UserAuthRoleRepo userAuthRoleRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

    public LeaseService(
            LeaseRepo leaseRepo,
            ApartmentRepo apartmentRepo,
            ApartmentOwnershipRepo ownershipRepo,
            UserProfileRepo userProfileRepo,
            UserAuthRepo userAuthRepo,
            UserAuthRoleRepo userAuthRoleRepo,
            RoleRepo roleRepo,
            BCryptPasswordEncoder passwordEncoder,
            CurrentUser currentUser
    ) {
        this.leaseRepo = leaseRepo;
        this.apartmentRepo = apartmentRepo;
        this.ownershipRepo = ownershipRepo;
        this.userProfileRepo = userProfileRepo;
        this.userAuthRepo = userAuthRepo;
        this.userAuthRoleRepo = userAuthRoleRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
    }

    public LeaseResponse createLease(LeaseCreateRequest request) {

        // 🔐 Authorization
        if (!currentUser.hasAnyRole("SUPER_ADMIN", "ADMIN")) {
            throw new AuthException("You are not allowed to create leases");
        }

        Apartment apartment = apartmentRepo.findById(request.getApartmentId())
                .orElseThrow(() -> new AuthException("Apartment not found"));

        if (!ownershipRepo.existsByApartmentIdAndOwnershipEndDateIsNull(apartment.getId())) {
            throw new AuthException("Apartment has no active owner and cannot be leased");
        }

        if (leaseRepo.existsByApartmentIdAndEndDateIsNull(apartment.getId())) {
            throw new AuthException("Apartment already has an active lease");
        }

        // 🔍 Validate resident selection
        int selectionCount = 0;
        if (request.getExistingUserProfileId() != null) selectionCount++;
        if (request.getNewUser() != null) selectionCount++;
        if (Boolean.TRUE.equals(request.getOwnerIsResident())) selectionCount++;

        if (selectionCount != 1) {
            throw new AuthException("Exactly one resident option must be selected");
        }

        UserProfile resident;

        // =========================
        // Option 1: Existing User
        // =========================
        if (request.getExistingUserProfileId() != null) {

            resident = userProfileRepo.findById(request.getExistingUserProfileId())
                    .orElseThrow(() -> new AuthException("User profile not found"));
        }

        // =========================
        // Option 2: New User
        // =========================
        else if (request.getNewUser() != null) {

            NewUserRequest nu = request.getNewUser();

            if (userProfileRepo.existsByPhonePrimary(nu.getPhone())) {
                throw new AuthException("Phone already exists");
            }

            if (userAuthRepo.existsByEmail(nu.getEmail())) {
                throw new AuthException("Email already exists");
            }

            if (userAuthRepo.existsByUsername(nu.getUsername())) {
                throw new AuthException("Username already exists");
            }

            UserProfile profile = UserProfile.builder()
                    .firstName(nu.getFirstName())
                    .lastName(nu.getLastName())
                    .gender(nu.getGender())
                    .phonePrimary(nu.getPhone())
                    .isActive(true)
                    .build();

            userProfileRepo.save(profile);

            UserAuth userAuth = UserAuth.builder()
                    .username(nu.getUsername())
                    .email(nu.getEmail())
                    .passwordHash(passwordEncoder.encode(nu.getPassword()))
                    .userProfile(profile)
                    .isActive(true)
                    .isLocked(false)
                    .build();

            userAuthRepo.save(userAuth);

            Role residentRole = roleRepo.findByRoleName("RESIDENT")
                    .orElseThrow(() -> new IllegalStateException("RESIDENT role not found"));

            UserAuthRole userAuthRole = UserAuthRole.builder()
                    .userAuth(userAuth)
                    .role(residentRole)
                    .isActive(true)
                    .build();

            userAuthRoleRepo.save(userAuthRole);

            resident = profile;
        }

        // =========================
        // Option 3: Owner of apartment
        // =========================
        else {

            ApartmentOwnership ownership =
                    ownershipRepo.findActiveOwnership(apartment.getId())
                            .orElseThrow(() ->
                                    new AuthException("Apartment has no active owner"));

            resident = ownership.getUserProfile();
        }

        // =========================
        // Create Lease
        // =========================
        Lease lease = Lease.builder()
                .apartment(apartment)
                .residentProfile(resident)
                .rentAmount(request.getRentAmount())
                .startDate(request.getStartDate())
                .build();

        leaseRepo.save(lease);

        return LeaseResponse.builder()
                .id(lease.getId())
                .apartmentId(apartment.getId())
                .residentProfileId(resident.getId())
                .rentAmount(lease.getRentAmount())
                .startDate(lease.getStartDate())
                .endDate(lease.getEndDate())
                .build();
    }
}

