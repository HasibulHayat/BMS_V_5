package com.example.mainsystem.repo;

import com.example.mainsystem.model.Role;
import com.example.mainsystem.model.UserAuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UserAuthRoleRepo extends JpaRepository<UserAuthRole, UUID> {
    List<UserAuthRole> findByRoleAndIsActiveTrue(Role role);
}
