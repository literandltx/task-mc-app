package com.literandltx.taskmcapp.controller;

import com.literandltx.taskmcapp.model.User;
import com.literandltx.taskmcapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UsersController {
    private final UserService userService;

    @PutMapping("/{id}/role")
    public void updateUserRole(@PathVariable Long id) {

    }

    @GetMapping("/me")
    public void getMyProfileInfo() {

    }

    @PutMapping("/me")
    public void updateProfileInfo() {

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
