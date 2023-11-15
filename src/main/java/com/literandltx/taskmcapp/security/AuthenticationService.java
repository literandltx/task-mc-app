package com.literandltx.taskmcapp.security;

import com.literandltx.taskmcapp.dto.user.UserLoginRequestDto;
import com.literandltx.taskmcapp.dto.user.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getUsername(), requestDto.getPassword())
        );

        String token = jwtUtil.generateToken(requestDto.getUsername());
        UserLoginResponseDto responseDto = new UserLoginResponseDto();
        responseDto.setToken(token);
        return responseDto;
    }
}
