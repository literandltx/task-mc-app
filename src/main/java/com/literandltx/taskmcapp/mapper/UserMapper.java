package com.literandltx.taskmcapp.mapper;

import com.literandltx.taskmcapp.config.MapperConfig;
import com.literandltx.taskmcapp.dto.user.profile.UpdateUserProfileRequestDto;
import com.literandltx.taskmcapp.dto.user.profile.UserProfileResponseDto;
import com.literandltx.taskmcapp.dto.user.register.UserRegistrationResponseDto;
import com.literandltx.taskmcapp.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserRegistrationResponseDto toModel(final User user);

    User toModel(final UpdateUserProfileRequestDto requestDto);

    UserProfileResponseDto toDto(final User user);
}
