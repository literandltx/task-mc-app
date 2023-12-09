package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.aspect.LogExecutionTime;
import com.literandltx.taskmcapp.dto.user.login.UserLoginRequestDto;
import com.literandltx.taskmcapp.dto.user.login.UserLoginResponseDto;
import com.literandltx.taskmcapp.dto.user.register.UserRegistrationRequestDto;
import com.literandltx.taskmcapp.dto.user.register.UserRegistrationResponseDto;
import com.literandltx.taskmcapp.security.AuthenticationService;
import com.literandltx.taskmcapp.service.app.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication controller")
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Login by username and password")
    @LogExecutionTime
    @PostMapping("/login")
    public UserLoginResponseDto login(
            @RequestBody @Valid final UserLoginRequestDto requestDto
    ) {
        return authenticationService.authenticate(requestDto);
    }

    @Operation(summary = "Register user")
    @LogExecutionTime
    @PostMapping("/register")
    public UserRegistrationResponseDto register(
            @RequestBody @Valid final UserRegistrationRequestDto requestDto
    ) {
        return userService.register(requestDto);
    }
}
