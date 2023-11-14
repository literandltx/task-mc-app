package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.dto.user.UserLoginRequestDto;
import com.literandltx.taskmcapp.dto.user.UserLoginResponseDto;
import com.literandltx.taskmcapp.dto.user.UserRegistrationRequestDto;
import com.literandltx.taskmcapp.dto.user.UserRegistrationResponseDto;
import com.literandltx.taskmcapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/login")
    public UserLoginResponseDto login(
            @RequestBody @Valid UserLoginRequestDto requestDto
    ) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/register")
    public UserRegistrationResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto requestDto
    ) {
        return userService.register(requestDto);
    }
}
