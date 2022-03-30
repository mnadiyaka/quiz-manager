package com.sigma.service;

import com.sigma.dto.SignUpUserDto;
import com.sigma.dto.SignUpUserResponseDto;
import com.sigma.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    public User findUserById(Long userId);

    public User findUserByUsername(String username);

    public SignUpUserResponseDto createUser(SignUpUserDto signUpDto);

    public void updateUser(User updatedUser, Long userId);

    public void deleteUser(Long userId);

}