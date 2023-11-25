package com.literandltx.taskmcapp.dto.user.profile;

import lombok.Data;

@Data
public class UpdateUserProfileRequestDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
