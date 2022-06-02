package com.sigma.service;

import com.sigma.model.dto.SignInUserDto;
import com.sigma.model.dto.SignInUserResponseDto;
import com.sigma.model.dto.SignUpUserDto;
import com.sigma.model.dto.SignUpUserResponseDto;
import com.sigma.model.dto.UserDto;
import com.sigma.model.entity.User;
import org.apache.tomcat.websocket.AuthenticationException;

import java.util.List;

public interface UserService {
    public List<UserDto> getAllUsers();

    public User findUserById(Long userId);

    public User findUserByUsername(String username);

    public SignUpUserResponseDto createUser(SignUpUserDto signUpDto);

    public void updateUser(User updatedUser, Long userId);

    public void deleteUser(Long userId);

    public SignInUserResponseDto login(SignInUserDto signInUserDto) throws AuthenticationException;
}