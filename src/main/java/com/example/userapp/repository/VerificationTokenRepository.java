package com.example.userapp.repository;

import com.example.userapp.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.userapp.model.User;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    Optional<VerificationToken> findByUser(User user);
}
