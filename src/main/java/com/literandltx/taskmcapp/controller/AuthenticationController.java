package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.aspect.LogExecutionTime;
import com.literandltx.taskmcapp.dto.user.login.UserLoginRequestDto;
import com.literandltx.taskmcapp.dto.user.login.UserLoginResponseDto;
import com.literandltx.taskmcapp.dto.user.register.UserRegistrationRequestDto;
import com.literandltx.taskmcapp.dto.user.register.UserRegistrationResponseDto;
import com.literandltx.taskmcapp.security.AuthenticationService;
import com.literandltx.taskmcapp.service.app.UserService;
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
    private final AuthenticationService authenticationService;

    @LogExecutionTime
    @PostMapping("/login")
    public UserLoginResponseDto login(
            @RequestBody @Valid UserLoginRequestDto requestDto
    ) {
        return authenticationService.authenticate(requestDto);
    }

    @LogExecutionTime
    @PostMapping("/register")
    public UserRegistrationResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto requestDto
    ) {
        return userService.register(requestDto);
    }
}
