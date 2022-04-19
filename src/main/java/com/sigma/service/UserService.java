package com.sigma.service;

import com.sigma.model.dto.SignInUserDto;
import com.sigma.model.dto.SignInUserResponseDto;
import com.sigma.model.dto.SignUpUserDto;
import com.sigma.model.dto.SignUpUserResponseDto;
import com.sigma.model.dto.UserDto;
import com.sigma.model.entity.User;
import org.apache.tomcat.websocket.AuthenticationException;

import java.util.Set;

public interface UserService {
    Set<UserDto> getAllUsers();

    User findUserById(Long userId);

    User findUserByUsername(String username);

    SignUpUserResponseDto createUser(SignUpUserDto signUpDto);

    void updateUser(User updatedUser, Long userId);

    void deleteUser(Long userId);

    SignInUserResponseDto login(SignInUserDto signInUserDto) throws AuthenticationException;
}