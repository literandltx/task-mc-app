package com.literandltx.taskmcapp.unit.service.user;

import com.literandltx.taskmcapp.dto.user.profile.UserProfileResponseDto;
import com.literandltx.taskmcapp.mapper.UserMapper;
import com.literandltx.taskmcapp.model.Role;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.RoleRepository;
import com.literandltx.taskmcapp.repository.UserRepository;
import com.literandltx.taskmcapp.service.app.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateUserRoleTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Test
    public void updateUserRole_Default() {
        // Given
        final Long userId = 1L;
        final Long roleId = 1L;
        final var user = new User()
                .setRoles(new HashSet<>());
        final var role = new Role();
        final var userAdmin = new User();
        final var userFromDb = new User();
        final var userResponse = new UserProfileResponseDto();
        final var expected = new UserProfileResponseDto();

        // When
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        Mockito.when(userRepository.save(user)).thenReturn(userFromDb);
        Mockito.when(userMapper.toDto(userFromDb)).thenReturn(userResponse);
        final UserProfileResponseDto actual = userService.updateUserRole(roleId, userId, userAdmin);

        // Then
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Mockito.verify(roleRepository, Mockito.times(1)).findById(roleId);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(userMapper, Mockito.times(1)).toDto(userFromDb);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void updateUserRole_UserNotFound() {
        // Given
        final Long userId = 1L;
        final Long roleId = 1L;
        final var userAdmin = new User();
        final String expectedMessage = "Cannot find user by id: " + userId;

        // When
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Exception actual = Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.updateUserRole(userId, roleId, userAdmin));

        // Then
        Assertions.assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    public void updateUserRole_RoleNotFound() {
        // Given
        final Long userId = 1L;
        final Long roleId = 1L;
        final var userAdmin = new User();
        final var user = new User();
        final String expectedMessage = "Cannot find role by id: " + userId;

        // When
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(roleRepository.findById(roleId)).thenReturn(Optional.empty());
        Exception actual = Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.updateUserRole(userId, roleId, userAdmin));

        // Then
        Assertions.assertEquals(expectedMessage, actual.getMessage());
    }

}
