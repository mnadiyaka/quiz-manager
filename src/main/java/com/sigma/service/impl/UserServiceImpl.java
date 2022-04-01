package com.sigma.service.impl;

import com.sigma.dto.SignUpUserDto;
import com.sigma.dto.SignUpUserResponseDto;
import com.sigma.model.User;
import com.sigma.repository.UserRepository;
import com.sigma.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        log.info("Getting list of users");
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long userId) {
        log.info("Searching for user with id {}", userId);
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public User findUserByUsername(String username) {
        log.info("Searching for user with username {}", username);
        if (userRepository.findByUsername(username) == null) {
            throw new EntityNotFoundException();
        }
        return userRepository.findByUsername(username);
    }

    @Override
    public SignUpUserResponseDto createUser(SignUpUserDto signUpDto) {
        log.info("Creating new user {}", signUpDto.getUsername());
        if (userRepository.findByUsername(signUpDto.getUsername()) != null) {
            return new SignUpUserResponseDto("failure", "user already exists");
        }

        User user = new User(signUpDto.getUsername(), passwordEncoder.encode(signUpDto.getPassword()), signUpDto.getRole());
        userRepository.save(user);
        return new SignUpUserResponseDto("success", "user created");
    }

    @Override
    public void updateUser(User updatedUser, Long userId) {
        User oldUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException());
        log.info("Updating user {}", oldUser.getUsername());
        oldUser.setUsername(updatedUser.getUsername());
        oldUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        oldUser.setRole(updatedUser.getRole());
        oldUser.setAdminLocation(updatedUser.getAdminLocation());
        userRepository.save(oldUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException();
        }
        log.info("Deleting user {}", userRepository.getById(userId).getUsername());
        userRepository.deleteById(userId);
    }
}