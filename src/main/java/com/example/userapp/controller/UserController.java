package com.example.userapp.controller;

import com.example.userapp.dto.LoginRequest;
import com.example.userapp.model.User;
import com.example.userapp.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.userapp.service.UserService;
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        try {userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully. Please check your email to verify.");
    } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest){
        try {
            userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok("Login successful!");
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
