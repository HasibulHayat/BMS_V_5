package com.example.mainsystem.service;

import com.example.mainsystem.dto.me.UserMeResponse;
import com.example.mainsystem.dto.me.UserProfileUpdateRequest;
import com.example.mainsystem.exception.AuthException;
import com.example.mainsystem.model.UserAuth;
import com.example.mainsystem.model.UserProfile;
import com.example.mainsystem.repo.UserAuthRepo;
import com.example.mainsystem.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserAuthRepo userAuthRepo;
    private final CurrentUser currentUser;

    public UserService(UserAuthRepo userAuthRepo, CurrentUser currentUser) {
        this.userAuthRepo = userAuthRepo;
        this.currentUser = currentUser;
    }

    private String normalize(String value) {
        if (value == null) return null;
        if (value.trim().isEmpty()) return null;
        return value.trim();
    }

    public UserMeResponse getCurrentUser() {

        UUID userId = currentUser.id();

        UserAuth userAuth = userAuthRepo.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));

        UserProfile profile = userAuth.getUserProfile();

        return UserMeResponse.builder()
                .id(userAuth.getId())
                .username(userAuth.getUsername())
                .email(userAuth.getEmail())
                .phone(profile.getPhonePrimary())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .middleName(profile.getMiddleName())
                .phoneSecondary(profile.getPhoneSecondary())
                .gender(profile.getGender())
                .bloodGroup(profile.getBloodGroup())
                .birthDate(profile.getBirthDate())
                .permanentAddress(profile.getPermanentAddress())
                .occupation(profile.getOccupation())
                .nationalId(profile.getNationalId())
                .passportId(profile.getPassportId())
                .drivingLicenseId(profile.getDrivingLicenseId())
                .emergencyContactName(profile.getEmergencyContactName())
                .emergencyContactPhone(profile.getEmergencyContactPhone())
                .roles(
                        userAuth.getRoles()
                                .stream()
                                .map(r -> r.getRole().getRoleName())
                                .collect(Collectors.toSet())
                )
                .isMarried(profile.getIsMarried())
                .isActive(userAuth.getIsActive())
                .notes(profile.getNotes())
                .build();
    }

    @Transactional
    public void updateMyProfile(UserProfileUpdateRequest req) {

        UUID userId = currentUser.id();

        UserAuth userAuth = userAuthRepo.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));

        UserProfile profile = userAuth.getUserProfile();

        // Mandatory (already validated)
        profile.setFirstName(req.getFirstName());
        profile.setLastName(req.getLastName());
        profile.setGender(req.getGender());

        // Optional (normalize blanks → null)
        profile.setMiddleName(normalize(req.getMiddleName()));
        profile.setPhoneSecondary(normalize(req.getPhoneSecondary()));
        profile.setBloodGroup(req.getBloodGroup());
        profile.setBirthDate(req.getBirthDate());
        profile.setIsMarried(req.getIsMarried());
        profile.setPermanentAddress(normalize(req.getPermanentAddress()));
        profile.setOccupation(normalize(req.getOccupation()));
        profile.setNationalId(normalize(req.getNationalId()));
        profile.setPassportId(normalize(req.getPassportId()));
        profile.setDrivingLicenseId(normalize(req.getDrivingLicenseId()));
        profile.setEmergencyContactName(normalize(req.getEmergencyContactName()));
        profile.setEmergencyContactPhone(normalize(req.getEmergencyContactPhone()));
        profile.setNotes(normalize(req.getNotes()));
    }
}
