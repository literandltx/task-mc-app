package com.literandltx.taskmcapp.dto.user;

import lombok.Data;

@Data
public class UserRegistrationResponseDto {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
