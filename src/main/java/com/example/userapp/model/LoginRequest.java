package com.example.userapp.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
