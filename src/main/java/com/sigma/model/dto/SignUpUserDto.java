package com.sigma.model.dto;

import com.sigma.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpUserDto {

    private String username;

    private String password;

    private Role role;
}
