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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
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
    public UserRegistrationResponseDto register(
            final UserRegistrationRequestDto request
    ) throws RuntimeException {
        if (userRepository.findByUsername(request.getEmail()).isPresent()) {
            log.info(String.format("User: %s, have already registered",
                    request.getUsername()));
            throw new RuntimeException(String.format("User: %s, have already registered",
                    request.getUsername()));
        }

        final User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRoles(Set.of(roleRepository.findRoleByName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find role by name: " + Role.RoleName.ROLE_USER.name()))));

        final User saved = userRepository.save(user);

        final Confirmation confirmation = new Confirmation(user);
        confirmationRepository.save(confirmation);

        emailService.sendEmailMessage(
                request.getUsername(), request.getEmail(), confirmation.getToken());

        log.info(String.format("User: %s, was registered successfully",
                user.getUsername()));

        return userMapper.toModel(saved);
    }

    @Override
    public UserProfileResponseDto verifyUserToken(
            final String token,
            final User user
    ) {
        if (token == null || user == null) {
            throw new RuntimeException("Token or/and user is null");
        }

        if (!confirmationRepository.existsByTokenAndUser(token, user)) {
            log.info(String.format("Cannot find user: %s, verify token: %s",
                    user.getUsername(), token));
            throw new EntityNotFoundException("Cannot find user verify token: " + token);
        }

        user.setIsConfirmed(true);

        log.info(String.format(
                "The user: %s, has been successfully verified",
                user.getUsername()));

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserProfileResponseDto updateUserRole(
            final Long roleId,
            final Long userId,
            final User adminUser
    ) {
        final User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find user by id: " + userId));
        final Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new EntityNotFoundException("Cannot find role by id: " + roleId));

        user.getRoles().add(role);

        log.info(String.format("A new role: %s, has been added for the user: %s",
                role.getName(), user.getUsername()));

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserProfileResponseDto getProfileInfo(
            final User user
    ) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }

        log.info(String.format("User: %s, get own profile info.",
                user.getUsername()));

        return userMapper.toDto(user);
    }

    @Override
    public UserProfileResponseDto updateProfileInfo(
            final UpdateUserProfileRequestDto requestDto,
            final User user
    ) {
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());

        log.info(String.format("Profile of user: %s, updated.",
                user.getUsername()));

        return userMapper.toDto(userRepository.save(user));
    }
}
