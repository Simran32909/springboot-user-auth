package com.example.userapp.service;

import com.example.userapp.model.VerificationToken;
import com.example.userapp.repository.UserRepository;
import com.example.userapp.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
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
    public void registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //return userRepository.save(user);

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
