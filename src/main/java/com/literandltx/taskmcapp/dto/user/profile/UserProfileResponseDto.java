package com.literandltx.taskmcapp.dto.user.profile;

import com.literandltx.taskmcapp.model.Role;
import java.util.Set;
import lombok.Data;

@Data
public class UserProfileResponseDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isConfirmed;
    private Set<Role> roles;
}
