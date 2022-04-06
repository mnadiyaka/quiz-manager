package com.sigma.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpUserResponseDto {

    private String status;

    private String message;
}