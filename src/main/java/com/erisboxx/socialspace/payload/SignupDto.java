package com.erisboxx.socialspace.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SignupDto {
    @NotEmpty
    @Size(min = 5, message = "Name must be 5 characters long")
    private String name;

    @NotEmpty
    @Size(min = 5, message = "Username must be 5 at least 5 characters long")
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
