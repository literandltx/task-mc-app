package com.literandltx.taskmcapp.service.app.impl;

import com.literandltx.taskmcapp.dto.user.profile.UpdateUserProfileRequestDto;
import com.literandltx.taskmcapp.dto.user.profile.UserProfileResponseDto;
import com.literandltx.taskmcapp.dto.user.register.UserRegistrationRequestDto;
import com.literandltx.taskmcapp.dto.user.register.UserRegistrationResponseDto;
import com.literandltx.taskmcapp.mapper.UserMapper;
import com.literandltx.taskmcapp.model.Confirmation;
import com.literandltx.taskmcapp.model.Role;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.ConfirmationRepository;
import com.literandltx.taskmcapp.repository.RoleRepository;
import com.literandltx.taskmcapp.repository.UserRepository;
import com.literandltx.taskmcapp.service.app.UserService;
import com.literandltx.taskmcapp.service.email.EmailService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ConfirmationRepository confirmationRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserMapper userMapper;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RuntimeException {
        if (userRepository.findByUsername(request.getEmail()).isPresent()) {
            throw new RuntimeException("User have already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRoles(Set.of(roleRepository.findRoleByName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find role by name: " + Role.RoleName.ROLE_USER.name()))));

        User saved = userRepository.save(user);

        Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);

        emailService.sendEmailMessage(
                request.getUsername(), request.getEmail(), confirmation.getToken());

        return userMapper.toModel(saved);
    }

    @Override
    public UserProfileResponseDto verifyUserToken(final String token, final User user) {
        if (token == null || user == null) {
            throw new RuntimeException("Token or/and user is null");
        }

        if (!confirmationRepository.existsByTokenAndUser(token, user)) {
            throw new EntityNotFoundException("Cannot find user verify token: " + token);
        }

        user.setIsConfirmed(true);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserProfileResponseDto updateUserRole(Long roleId, Long userId, User adminUser) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find user by id: " + userId));
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find role by id: " + roleId));

        user.getRoles().add(role);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserProfileResponseDto getProfileInfo(User user) {
        return userMapper.toDto(user);
    }

    @Override
    public UserProfileResponseDto updateProfileInfo(
            UpdateUserProfileRequestDto requestDto,
            User user
    ) {
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());

        return userMapper.toDto(userRepository.save(user));
    }
}
