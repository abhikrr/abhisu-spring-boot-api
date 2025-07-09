package com.abhisu.abhisu_spring_boot_api.repository;

import com.abhisu.abhisu_spring_boot_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
