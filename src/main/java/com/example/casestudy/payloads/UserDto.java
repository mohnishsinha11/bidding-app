package com.example.casestudy.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Integer userId;

    @NotEmpty
    @Size(min = 4, message = "Username must be of minimum of 4 characters")
    private String name;

    @Email(message = "Email address is not valid")
    @NotEmpty
    private String email;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, max = 15, message = "Password must be min of 8 chars and max of 15 chars")
    private String password;
}
