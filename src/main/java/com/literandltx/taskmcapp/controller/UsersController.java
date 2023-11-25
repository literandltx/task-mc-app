package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.dto.user.profile.UpdateUserProfileRequestDto;
import com.literandltx.taskmcapp.dto.user.profile.UserProfileResponseDto;
import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UsersController {
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/role")
    public UserProfileResponseDto updateUserRole(
            @RequestParam("roleId") Long roleId,
            @RequestParam("userId") Long userId,
            Authentication authentication
    ) {
        User adminUser = (User) authentication.getPrincipal();

        return userService.updateUserRole(roleId, userId, adminUser);
    }

    @GetMapping("/me")
    public UserProfileResponseDto getMyProfileInfo(
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return userService.getProfileInfo(user);
    }

    @PutMapping("/me")
    public UserProfileResponseDto updateProfileInfo(
            @RequestBody UpdateUserProfileRequestDto requestDto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return userService.updateProfileInfo(requestDto, user);
    }

    @PostMapping("/confirm")
    public Boolean confirmUserAccount(
            @RequestParam(name = "token") String token,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        return userService.verifyUserToken(token, user);
    }
}
