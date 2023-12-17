package com.literandltx.taskmcapp.security;

import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            log.info(String.format(
                    "Someone tried to authenticate as: \"%s\". User is not registered",
                    username));
            throw new RuntimeException(String.format(
                    "Cannot find user by username: \"%s\". User is not registered",
                    username));
        }

        return user.get();
    }
}
