package com.example.userapp.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Async
    public void sendVerificationEmail(String toEmail, String verificationLink){
        logger.info("Sending verification email to {}", toEmail);
        logger.info("Verification link: {}", verificationLink);
    }
}
