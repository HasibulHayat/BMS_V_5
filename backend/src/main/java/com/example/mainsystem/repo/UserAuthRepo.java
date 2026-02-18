package com.example.mainsystem.repo;

import com.example.mainsystem.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserAuthRepo extends JpaRepository<UserAuth, UUID> {
    Optional<UserAuth> findByEmail(String email);

    Optional<UserAuth> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("""
        select r.roleName
        from UserAuthRole uar
        join uar.role r
        join uar.userAuth ua
        where ua.email = :email
          and uar.isActive = true
        """)
    Set<String> findActiveRoleNamesByEmail(String email);
}
