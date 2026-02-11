package com.example.schedule_upgrade.user.repository;

import com.example.schedule_upgrade.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@NotBlank String email);
}
