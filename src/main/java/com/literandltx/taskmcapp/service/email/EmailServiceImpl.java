package com.literandltx.taskmcapp.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendEmailMessage(
            final String name,
            final String to,
            final String token
    ) {
        try {
            final SimpleMailMessage message = new SimpleMailMessage();

            message.setSubject("Account Verification");
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(getGreetingMessage(name, token));

            emailSender.send(message);

            log.info(String.format(
                    "Account verification message to email: %s, was send successfully",
                    to));
        } catch (final Exception exception) {
            log.info(String.format(
                    "The sending of the email to: %s, was interrupted for reason: %s",
                    to, exception.getMessage()));
            throw new RuntimeException(exception.getMessage());
        }
    }

    private String getGreetingMessage(final String name, final String token) {
        return "Hello, " + name + ", seemed you register to task-mc-app. "
                + "To confirm your account send token: " + token;
    }
}
