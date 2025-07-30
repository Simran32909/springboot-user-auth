package com.example.userapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import com.example.userapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}