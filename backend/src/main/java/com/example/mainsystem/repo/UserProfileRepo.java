package com.example.mainsystem.repo;

import com.example.mainsystem.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserProfileRepo extends JpaRepository<UserProfile, UUID> {
    boolean existsByPhonePrimary(String phonePrimary);
}
