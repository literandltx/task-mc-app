package com.literandltx.taskmcapp.dto.user;

import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    private String username;
    private String email;
    private String password;
    private String repeatPassword;
    private String firstName;
    private String lastName;
}
