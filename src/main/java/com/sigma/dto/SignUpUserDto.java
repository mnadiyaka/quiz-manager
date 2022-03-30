package com.sigma.dto;

import com.sigma.model.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpUserDto {

    private String username;

    private String password;

    private Role role;
}
