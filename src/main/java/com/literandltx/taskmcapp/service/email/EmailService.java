package com.literandltx.taskmcapp.service.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendEmailMessage(String name, String to, String token);
}
