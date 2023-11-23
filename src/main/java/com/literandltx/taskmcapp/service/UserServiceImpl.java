package com.literandltx.taskmcapp.service;

import com.literandltx.taskmcapp.dto.user.UserRegistrationRequestDto;
import com.literandltx.taskmcapp.dto.user.UserRegistrationResponseDto;
import com.literandltx.taskmcapp.mapper.UserMapper;
import com.literandltx.taskmcapp.model.Confirmation;
import com.literandltx.taskmcapp.model.Role;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.ConfirmationRepository;
import com.literandltx.taskmcapp.repository.RoleRepository;
import com.literandltx.taskmcapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final ConfirmationRepository confirmationRepository;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RuntimeException {
        if (userRepository.findByUsername(request.getEmail()).isPresent()) {
            throw new RuntimeException("Unable to complete registration");
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

        // TODO: 11/23/23 send token to user email
        
        return userMapper.toModel(saved);
    }

    @Override
    public Boolean verifyUserToken(String token, User user) {
        Confirmation confirmation = confirmationRepository.findByToken(token).orElseThrow(
                () -> new RuntimeException("Cannot find token: " + token));

        if (
                !Objects.equals(user.getId(), confirmation.getUser().getId()) ||
                !Objects.equals(confirmation.getToken(), token)
        ) {
            throw new RuntimeException("Invalid user token");
        }

        user.setIsConfirmed(true);

        return Boolean.TRUE;
    }
}
