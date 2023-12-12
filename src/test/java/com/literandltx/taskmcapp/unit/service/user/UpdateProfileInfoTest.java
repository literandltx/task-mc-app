package com.literandltx.taskmcapp.unit.service.user;

import com.literandltx.taskmcapp.dto.user.profile.UpdateUserProfileRequestDto;
import com.literandltx.taskmcapp.dto.user.profile.UserProfileResponseDto;
import com.literandltx.taskmcapp.mapper.UserMapper;
import com.literandltx.taskmcapp.model.Role;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.UserRepository;
import com.literandltx.taskmcapp.service.app.impl.UserServiceImpl;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UpdateProfileInfoTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Test
    public void updateProfileInfo_Default() {
        // Given
        final String email = "mail1@email.com";
        final String username = "username1";
        final String firstname = "firstname1";
        final String lastname = "lastname1";
        final Set<Role> roles = Set.of(new Role()
                .setId(1L)
                .setName(Role.RoleName.ROLE_USER));
        final Boolean isConfirmed = Boolean.TRUE;

        final var user = new User()
                .setId(1L)
                .setUsername("username")
                .setEmail("mail@email.com")
                .setPassword("password")
                .setFirstName("firstname")
                .setLastName("lastname")
                .setIsConfirmed(isConfirmed)
                .setRoles(roles);
        final var userRequest = new UpdateUserProfileRequestDto()
                .setEmail(email)
                .setUsername(username)
                .setFirstName(firstname)
                .setLastName(lastname);
        final var expected = new UserProfileResponseDto()
                .setEmail(email)
                .setUsername(username)
                .setFirstName(firstname)
                .setLastName(lastname)
                .setIsConfirmed(isConfirmed)
                .setRoles(roles);

        // When
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userMapper.toDto(user)).thenReturn(expected);
        final var actual = userService.updateProfileInfo(userRequest, user);

        // Then
        Assertions.assertEquals(expected, actual);
    }

}
