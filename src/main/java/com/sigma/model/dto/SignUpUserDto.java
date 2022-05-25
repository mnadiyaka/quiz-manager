package com.sigma.model.dto;

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
}