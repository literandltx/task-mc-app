package com.literandltx.taskmcapp.service;

import com.literandltx.taskmcapp.dto.user.UserRegistrationRequestDto;
import com.literandltx.taskmcapp.dto.user.UserRegistrationResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RuntimeException;
}
