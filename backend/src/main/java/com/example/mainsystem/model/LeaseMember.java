package com.example.mainsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"lease_id", "member_profile_id"}
        )
)
public class LeaseMember extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lease_id", nullable = false)
    private Lease lease;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_profile_id", nullable = false)
    private UserProfile memberProfile;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RelationshipWithResident relationshipWithResident;
}