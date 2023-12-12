package com.literandltx.taskmcapp.dto.user.profile;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UpdateUserProfileRequestDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
