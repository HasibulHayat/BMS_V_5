package com.example.mainsystem.repo;

import com.example.mainsystem.model.ApartmentOwnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApartmentOwnershipRepo
        extends JpaRepository<ApartmentOwnership, UUID> {

    @Query("""
        select coalesce(sum(o.ownershipPercentage), 0)
        from ApartmentOwnership o
        where o.apartment.id = :apartmentId
          and o.ownershipEndDate is null
    """)
    BigDecimal totalActiveOwnership(UUID apartmentId);

    boolean existsByApartmentIdAndUserProfileIdAndOwnershipEndDateIsNull(
            UUID apartmentId,
            UUID userProfileId
    );

    @Query("""
    select o
    from ApartmentOwnership o
    where o.apartment.id = :apartmentId
      and o.ownershipEndDate is null
    """)
    Optional<ApartmentOwnership> findActiveOwnership(UUID apartmentId);

    boolean existsByApartmentIdAndOwnershipEndDateIsNull(UUID apartmentId);

    @Query("""
    select distinct b.name
    from ApartmentOwnership ao
    join ao.apartment a
    join a.building b
    where ao.userProfile.id = :ownerProfileId
      and ao.ownershipEndDate is null
    """)
    List<String> findActiveBuildingNamesByOwner(UUID ownerProfileId);

}
