package com.literandltx.taskmcapp.security;

import com.literandltx.taskmcapp.dto.user.login.UserLoginRequestDto;
import com.literandltx.taskmcapp.dto.user.login.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(final UserLoginRequestDto requestDto) {
        log.info(String.format(
                "Someone with username: \"%s\", try to login.",
                requestDto.getUsername()));

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getUsername(), requestDto.getPassword())
        );

        final String token = jwtUtil.generateToken(requestDto.getUsername());
        final UserLoginResponseDto responseDto = new UserLoginResponseDto();
        responseDto.setToken(token);

        log.info(String.format(
                "Username: \"%s\", authenticated. JTW token generated successfully",
                requestDto.getUsername()));

        return responseDto;
    }
}
