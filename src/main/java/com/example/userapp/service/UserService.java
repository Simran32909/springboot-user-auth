package com.example.userapp.service;

import com.example.userapp.model.RegisterRequest;
import com.example.userapp.model.VerificationToken;
import com.example.userapp.repository.UserRepository;
import com.example.userapp.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.userapp.model.User;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Autowired
    public VerificationTokenRepository tokenRepository;

    @Autowired
    public EmailService emailService;

    @Transactional
    public void registerUser(@Valid RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use!");
        }
        User user = new User();
        user.setFullName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnable(false);

        userRepository.save(user);

        VerificationToken token = new VerificationToken(user);
        tokenRepository.save(token);

        String verificationLink = "http://localhost:8080/api/verify?token=" + token.getToken();
        emailService.sendVerificationEmail(user.getEmail(), verificationLink);
    }

    public User loginUser(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }
        if (!user.isEnabled()) {
            throw new RuntimeException("User not verified!");
        }
        return user;
    }

}
