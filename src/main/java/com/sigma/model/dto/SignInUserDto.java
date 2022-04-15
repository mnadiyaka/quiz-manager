package com.sigma.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SignInUserDto {

    @NotNull
    private String username;

    @NotNull
    private String password;
}