package com.example.userapp.controller;

import com.example.userapp.dto.LoginRequest;
import com.example.userapp.model.RegisterRequest;
import com.example.userapp.model.User;
import com.example.userapp.model.VerificationToken;
import com.example.userapp.repository.UserRepository;
import com.example.userapp.repository.VerificationTokenRepository;
import com.example.userapp.service.EmailService;
import com.example.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, VerificationTokenRepository tokenRepository, EmailService emailService){
        this.userService=userService;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully. Please check your email to verify.");
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

    @GetMapping("/resend-verification")
    public ResponseEntity<String> resendVerification(@RequestParam String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found!");
        }

        User user = optionalUser.get();

        if (user.isEnabled()) {
            return ResponseEntity.badRequest().body("User already verified!");
        }

        tokenRepository.findByUser(user).ifPresent(tokenRepository::delete);

        VerificationToken newToken = new VerificationToken(user);

        tokenRepository.save(newToken);

        emailService.sendVerificationEmail(user.getEmail(), newToken.getToken());

        return ResponseEntity.ok("Verification email sent!");

    }

}
