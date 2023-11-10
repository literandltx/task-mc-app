package com.literandltx.taskmcapp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    @PostMapping("/login")
    public void login() {

    }

    @PostMapping("/register")
    public void register() {

    }
}
