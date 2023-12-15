package com.literandltx.taskmcapp.service.app;

import com.literandltx.taskmcapp.dto.user.profile.UpdateUserProfileRequestDto;
import com.literandltx.taskmcapp.dto.user.profile.UserProfileResponseDto;
import com.literandltx.taskmcapp.dto.user.register.UserRegistrationRequestDto;
import com.literandltx.taskmcapp.dto.user.register.UserRegistrationResponseDto;
import com.literandltx.taskmcapp.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserRegistrationResponseDto register(
            final UserRegistrationRequestDto request
    ) throws RuntimeException;

    UserProfileResponseDto verifyUserToken(
            final String token,
            final User user
    );

    UserProfileResponseDto updateUserRole(
            final Long roleId,
            final Long userId,
            final User adminUser
    );

    UserProfileResponseDto getProfileInfo(
            final User user
    );

    UserProfileResponseDto updateProfileInfo(
            final UpdateUserProfileRequestDto requestDto,
            final User user
    );
}
