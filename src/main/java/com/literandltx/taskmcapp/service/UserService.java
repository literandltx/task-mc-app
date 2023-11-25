package com.literandltx.taskmcapp.service;

import com.literandltx.taskmcapp.dto.user.UserRegistrationRequestDto;
import com.literandltx.taskmcapp.dto.user.UserRegistrationResponseDto;
import com.literandltx.taskmcapp.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RuntimeException;

    Boolean verifyUserToken(String token, User user);
}
