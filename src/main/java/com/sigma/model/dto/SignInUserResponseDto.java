package com.sigma.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInUserResponseDto {

    private String type;

    private String token;
}
