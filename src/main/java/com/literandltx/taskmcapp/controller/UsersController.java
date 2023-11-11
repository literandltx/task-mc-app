package com.literandltx.taskmcapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UsersController {
    @PutMapping("/{id}/role")
    public void updateUserRole(@PathVariable Long id) {

    }

    @GetMapping("/me")
    public void getMyProfileInfo() {

    }

    @PutMapping("/me")
    public void updateProfileInfo() {

    }
}
