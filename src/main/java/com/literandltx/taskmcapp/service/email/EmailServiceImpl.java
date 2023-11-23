package com.literandltx.taskmcapp.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendEmailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setSubject("Account Verification");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(getGreetingMessage(name, token));

            emailSender.send(message);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private String getGreetingMessage(String name, String token) {
        return "Hello, " + name + ", seemed you register to task-mc-app. "
                + "To confirm your account send token: " + token;
    }
}
