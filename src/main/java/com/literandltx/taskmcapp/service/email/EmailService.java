package com.literandltx.taskmcapp.service.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmailMessage(
            final String name,
            final String to,
            final String token);
}
