package com.abhisu.abhisu_spring_boot_api.repository;

import com.abhisu.abhisu_spring_boot_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
