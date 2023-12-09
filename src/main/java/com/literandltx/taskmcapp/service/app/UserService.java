package com.literandltx.taskmcapp.service.app;

import com.literandltx.taskmcapp.dto.user.profile.UpdateUserProfileRequestDto;
import com.literandltx.taskmcapp.dto.user.profile.UserProfileResponseDto;
import com.literandltx.taskmcapp.dto.user.register.UserRegistrationRequestDto;
import com.literandltx.taskmcapp.dto.user.register.UserRegistrationResponseDto;
import com.literandltx.taskmcapp.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RuntimeException;

    UserProfileResponseDto verifyUserToken(String token, User user);

    UserProfileResponseDto updateUserRole(Long roleId, Long userId, User adminUser);

    UserProfileResponseDto getProfileInfo(User user);

    UserProfileResponseDto updateProfileInfo(UpdateUserProfileRequestDto requestDto, User user);
}
