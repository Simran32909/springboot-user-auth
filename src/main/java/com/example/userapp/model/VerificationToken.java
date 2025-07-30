package com.example.userapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String token;
    private LocalDateTime expiryDate;

    public String getToken(){
        return token;
    }

    public LocalDateTime getExpiryDate(){
        return expiryDate;
    }

    public Long getId(){
        return id;
    }

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser(){
        return user;
    }

    public VerificationToken(){}

    public VerificationToken(User user){
        this.user=user;
        this.token= UUID.randomUUID().toString();
        this.expiryDate= LocalDateTime.now().plusMinutes(30);
    }
}
