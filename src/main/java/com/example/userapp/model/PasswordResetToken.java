package com.example.userapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne
    private User user;

    private LocalDateTime expiryDate;

    public PasswordResetToken(User user){
        this.user=user;
        this.token=java.util.UUID.randomUUID().toString();
        this.expiryDate=LocalDateTime.now().plusHours(1);
    }
}
