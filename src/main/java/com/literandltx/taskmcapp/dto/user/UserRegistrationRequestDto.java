package com.literandltx.taskmcapp.dto.user;

import com.literandltx.taskmcapp.validation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@FieldMatch(
        first = "password",
        second = "repeatPassword",
        message = "Password and repeat password shouldn't be empty and should be equal"
)
public class UserRegistrationRequestDto {
    @Size.List(value = {
            @Size(min = 3, message = "Username length need to be more than 3"),
            @Size(max = 100, message = "Username length need to be less than 100")
    })
    private String username;

    @Email
    private String email;

    @Size(min = 8, max = 256)
    private String password;

    @Size(min = 8, max = 256)
    private String repeatPassword;

    @Size.List(value = {
            @Size(min = 2, message = "Username length need to be more than 2"),
            @Size(max = 50, message = "Username length need to be less than 50")
    })
    private String firstName;

    @Size.List(value = {
            @Size(min = 2, message = "Username length need to be more than 2"),
            @Size(max = 50, message = "Username length need to be less than 50")
    })
    private String lastName;
}
