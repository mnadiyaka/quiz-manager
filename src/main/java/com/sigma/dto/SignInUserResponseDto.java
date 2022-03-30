package com.sigma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class SignInUserResponseDto {

    private String type;

    private String token;
}
