package com.sigma.model.dto;

import com.sigma.model.entity.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class SignUpUserDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Role role;
}