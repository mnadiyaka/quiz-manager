package com.sigma.service;

import com.sigma.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    public User findUserById(Long userId);

    public User createUser(User user);

    public void updateUser(User updatedUser, Long userId);

    public void deleteUser(Long userId);

}