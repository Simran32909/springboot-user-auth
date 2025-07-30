package com.example.userapp.controller;

import com.example.userapp.repository.UserRepository;
import com.example.userapp.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.userapp.model.VerificationToken;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class VerificationController {
    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token){
        Optional<VerificationToken> optionalToken = tokenRepository.findByToken(token);

        if (optionalToken.isEmpty()) {
            return "Invalid verification token!";
        }
        VerificationToken verificationToken = optionalToken.get();

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "Verification token expired!";
        }

        var user = verificationToken.getUser();
        user.setEnable(true);
        userRepository.save(user);

        tokenRepository.delete(verificationToken); //delete token after use

        return "User verified successfully!";
    }
}
