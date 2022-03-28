package com.sigma.service.impl;

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
    public User createUser(User user) {
        log.info("Creating new user {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
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
        userRepository.deleteById(Long.valueOf(userId));
    }
}