package com.example.userapp.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private JavaMailSender mailSender;

    @Async
    public void sendVerificationEmail(String toEmail, String verificationLink){
        logger.info("Sending verification email to {}", toEmail);
        logger.info("Verification link: {}", verificationLink);
    }

    @Async
    public void sendSimpleMessage(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("simransandral05@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String toEmail, String resetLink){
        String subject = "Reset your password";
        String body = "To reset your password, please click on the link below:\n" + resetLink;
        sendSimpleMessage(toEmail, subject, body);
    }

}
