package com.example.mainsystem.service;

import com.example.mainsystem.dto.owner.OwnerCreationRequest;
import com.example.mainsystem.dto.owner.OwnerCreationResponse;
import com.example.mainsystem.dto.owner.OwnerListResponse;
import com.example.mainsystem.exception.AuthException;
import com.example.mainsystem.model.Role;
import com.example.mainsystem.model.UserAuth;
import com.example.mainsystem.model.UserAuthRole;
import com.example.mainsystem.model.UserProfile;
import com.example.mainsystem.repo.*;
import com.example.mainsystem.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OwnerService {
    private final UserProfileRepo userProfileRepo;
    private final UserAuthRepo userAuthRepo;
    private final UserAuthRoleRepo userAuthRoleRepo;
    private final RoleRepo roleRepo;
    private final CurrentUser currentUser;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ApartmentOwnershipRepo apartmentOwnershipRepo;

    public OwnerService(
            UserProfileRepo userProfileRepo,
            UserAuthRepo userAuthRepo,
            RoleRepo roleRepo,
            UserAuthRoleRepo userAuthRoleRepo,
            BCryptPasswordEncoder passwordEncoder,
            CurrentUser currentUser,
            ApartmentOwnershipRepo apartmentOwnershipRepo
    ) {
        this.userProfileRepo = userProfileRepo;
        this.userAuthRepo = userAuthRepo;
        this.roleRepo = roleRepo;
        this.userAuthRoleRepo = userAuthRoleRepo;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
        this.apartmentOwnershipRepo = apartmentOwnershipRepo;
    }

    public OwnerCreationResponse createOwner(OwnerCreationRequest request) {

        if (!currentUser.hasAnyRole("ADMIN", "SUPER_ADMIN")) {
            throw new AuthException("You are not allowed to create owners");
        }

        if (userProfileRepo.existsByPhonePrimary(request.getPhone())) {
            throw new AuthException("Phone already exists");
        }

        if (userAuthRepo.existsByEmail(request.getEmail())) {
            throw new AuthException("Email already exists");
        }

        if (userAuthRepo.existsByUsername(request.getUsername())) {
            throw new AuthException("Username already exists");
        }

        UserProfile profile = UserProfile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .phonePrimary(request.getPhone())
                .isActive(true)
                .build();

        userProfileRepo.save(profile);

        UserAuth userAuth = UserAuth.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .userProfile(profile)
                .isActive(true)
                .isLocked(false)
                .build();

        userAuthRepo.save(userAuth);

        Role ownerRole = roleRepo.findByRoleName("OWNER")
                .orElseThrow(() -> new IllegalStateException("OWNER role not found"));

        UserAuthRole userAuthRole = UserAuthRole.builder()
                .userAuth(userAuth)
                .role(ownerRole)
                .isActive(true)
                .build();

        userAuthRoleRepo.save(userAuthRole);

        return OwnerCreationResponse.builder()
                .profileId(profile.getId())
                .username(userAuth.getUsername())
                .email(userAuth.getEmail())
                .build();
    }

    public List<OwnerListResponse> getAllOwners() {

        if (!currentUser.hasAnyRole("SUPER_ADMIN", "ADMIN")) {
            throw new AuthException("You are not allowed to view owners");
        }

        Role ownerRole = roleRepo.findByRoleName("OWNER")
                .orElseThrow(() -> new IllegalStateException("OWNER role not found"));

        return userAuthRoleRepo.findByRoleAndIsActiveTrue(ownerRole)
                .stream()
                .map(UserAuthRole::getUserAuth)
                .map(userAuth -> {

                    UserProfile profile = userAuth.getUserProfile();

                    List<String> buildings =
                            apartmentOwnershipRepo.findActiveBuildingNamesByOwner(
                                    profile.getId()
                            );

                    return OwnerListResponse.builder()
                            .id(userAuth.getId())
                            .firstName(profile.getFirstName())
                            .lastName(profile.getLastName())
                            .phone(profile.getPhonePrimary())
                            .email(userAuth.getEmail())
                            .isActive(userAuth.getIsActive())
                            .buildings(buildings)
                            .build();
                })
                .toList();
    }

}
