package com.literandltx.taskmcapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
