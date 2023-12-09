package com.literandltx.taskmcapp.unit.service.user;

import com.literandltx.taskmcapp.dto.user.profile.UserProfileResponseDto;
import com.literandltx.taskmcapp.mapper.UserMapper;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.ConfirmationRepository;
import com.literandltx.taskmcapp.repository.UserRepository;
import com.literandltx.taskmcapp.service.app.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VerifyTokenTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private ConfirmationRepository confirmationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    public void verifyToken_ValidToken_True() {
        // Given
        final Long id = 1L;
        final String token = "token";

        final var user = new User()
                .setId(id);
        final var expected = new UserProfileResponseDto()
                .setIsConfirmed(Boolean.TRUE);

        // When
        Mockito.when(confirmationRepository.existsByTokenAndUser(token, user))
                .thenReturn(Boolean.TRUE);
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Mockito.when(userMapper.toDto(user)).thenReturn(expected);

        final var actual = userService.verifyUserToken(token, user);

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void verifyToken_ValidToken_False() {
        // Given
        final Long id = 1L;
        final String token = "token";
        final var user = new User()
                .setId(id);
        final String expected = "Cannot find user verify token: " + token;

        // When
        Mockito.when(confirmationRepository.existsByTokenAndUser(token, user))
                .thenReturn(Boolean.FALSE);

        Exception actual = Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.verifyUserToken(token, user));

        // Then
        Assertions.assertEquals(expected, actual.getMessage());
    }

    @Test
    public void verifyToken_NullCheck_TokenNull() {
        // Given
        final String token = "token";
        final String expected = "Token or/and user is null";

        // When
        Exception actual = Assertions.assertThrows(RuntimeException.class,
                () -> userService.verifyUserToken(token, null));

        // Then
        Assertions.assertEquals(expected, actual.getMessage());
    }

    @Test
    public void verifyToken_NullCheck_UserNull() {
        // Given
        final Long id = 1L;
        final var user = new User()
                .setId(id);
        final String expected = "Token or/and user is null";

        // When
        Exception actual = Assertions.assertThrows(RuntimeException.class,
                () -> userService.verifyUserToken(null, user));

        // Then
        Assertions.assertEquals(expected, actual.getMessage());
    }

    @Test
    public void verifyToken_NullCheck_TokenAndUserNull() {
        // Given
        final String expected = "Token or/and user is null";

        // When
        Exception actual = Assertions.assertThrows(RuntimeException.class,
                () -> userService.verifyUserToken(null, null));

        // Then
        Assertions.assertEquals(expected, actual.getMessage());
    }
}
