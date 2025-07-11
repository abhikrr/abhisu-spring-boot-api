package com.abhisu.abhisu_spring_boot_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Example: ROLE_USER, ROLE_ADMIN
}
