package com.example.userapp.controller;

import com.example.userapp.model.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.example.userapp.model.RegisterRequest;
import com.example.userapp.service.UserService;
import com.example.userapp.model.User;


@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserService userService;

    @Operation(
            summary = "Register a new user",
            description = "Registers a new user and sends a verification code"
    )

    @ApiResponse(
            responseCode = "200",
            description = "User Registered Successfully"
    )

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request){
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully. Please check your email to verify.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest){
        try {
            User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok("Login successful!" + user.getFullName());
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
