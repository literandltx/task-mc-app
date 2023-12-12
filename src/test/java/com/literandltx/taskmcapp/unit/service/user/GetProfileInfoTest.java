package com.literandltx.taskmcapp.unit.service.user;

import com.literandltx.taskmcapp.dto.user.profile.UserProfileResponseDto;
import com.literandltx.taskmcapp.mapper.UserMapper;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.service.app.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetProfileInfoTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @Test
    public void getProfileInfo_Default() {
        // Given
        final var id = 1L;
        final var username = "username";
        final var password = "password";
        final var email = "mail@email.com";
        final var user = new User()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setEmail(email)
                .setIsConfirmed(Boolean.TRUE);
        final var expected = new UserProfileResponseDto()
                .setUsername(username)
                .setEmail(email)
                .setIsConfirmed(Boolean.TRUE);

        // When
        Mockito.when(userMapper.toDto(user))
                .thenReturn(expected);
        final var actual = userService.getProfileInfo(user);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getProfileInfo_UserNull() {
        // Given
        final String expected = "User is null";

        // When
        Exception actual = Assertions.assertThrows(RuntimeException.class,
                () -> userService.getProfileInfo(null));

        // Then
        Assertions.assertEquals(expected, actual.getMessage());
    }
}
