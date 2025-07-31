package com.example.userapp.repository;

import com.example.userapp.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository {
    Optional<PasswordResetToken> findByToken(String token);
}
