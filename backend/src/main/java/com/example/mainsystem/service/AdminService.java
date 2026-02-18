package com.example.mainsystem.service;

import com.example.mainsystem.dto.admin.AdminCreationRequest;
import com.example.mainsystem.dto.admin.AdminCreationResponse;
import com.example.mainsystem.dto.admin.AdminListResponse;
import com.example.mainsystem.exception.AuthException;
import com.example.mainsystem.model.Role;
import com.example.mainsystem.model.UserAuth;
import com.example.mainsystem.model.UserAuthRole;
import com.example.mainsystem.model.UserProfile;
import com.example.mainsystem.repo.RoleRepo;
import com.example.mainsystem.repo.UserAuthRepo;
import com.example.mainsystem.repo.UserAuthRoleRepo;
import com.example.mainsystem.repo.UserProfileRepo;
import com.example.mainsystem.security.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class AdminService {
    private final UserProfileRepo userProfileRepo;
    private final UserAuthRepo userAuthRepo;
    private final RoleRepo roleRepo;
    private final UserAuthRoleRepo userAuthRoleRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

    public AdminService(
            UserProfileRepo userProfileRepo, UserAuthRepo userAuthRepo, RoleRepo roleRepo,
            UserAuthRoleRepo userAuthRoleRepo, BCryptPasswordEncoder passwordEncoder, CurrentUser currentUser
    ) {
        this.userProfileRepo = userProfileRepo;
        this.userAuthRepo = userAuthRepo;
        this.roleRepo = roleRepo;
        this.userAuthRoleRepo = userAuthRoleRepo;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
    }

    public AdminCreationResponse createAdmin(AdminCreationRequest request) {
        if (!currentUser.hasRole("SUPER_ADMIN")) {
            throw new AuthException("You are not allowed to perform this action");
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

        Role adminRole = roleRepo.findByRoleName("ADMIN")
                .orElseThrow(() -> new IllegalStateException("ADMIN role not found"));

        UserAuthRole userAuthRole = UserAuthRole.builder()
                .userAuth(userAuth)
                .role(adminRole)
                .isActive(true)
                .build();

        userAuthRoleRepo.save(userAuthRole);

        return AdminCreationResponse.builder()
                .username(userAuth.getUsername())
                .email(userAuth.getEmail())
                .build();
    }

    public List<AdminListResponse> getAllAdmins() {

        if (!currentUser.hasRole("SUPER_ADMIN")) {
            throw new AuthException("You are not allowed to view admins");
        }

        Role adminRole = roleRepo.findByRoleName("ADMIN")
                .orElseThrow(() -> new IllegalStateException("ADMIN role not found"));

        return userAuthRoleRepo.findByRoleAndIsActiveTrue(adminRole)
                .stream()
                .map(UserAuthRole::getUserAuth)
                .map(userAuth -> {
                    UserProfile p = userAuth.getUserProfile();
                    return AdminListResponse.builder()
                            .id(userAuth.getId())
                            .firstName(p.getFirstName())
                            .lastName(p.getLastName())
                            .phone(p.getPhonePrimary())
                            .email(userAuth.getEmail())
                            .isActive(userAuth.getIsActive())
                            .build();
                })
                .toList();
    }

}
