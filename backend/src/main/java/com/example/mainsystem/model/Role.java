package com.example.mainsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseModel {
    @Column(unique = true, nullable = false, length = 20)
    private String roleName;

    @Column(nullable = false)
    private String description;
}
