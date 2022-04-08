package com.sigma.service.impl;

import com.sigma.JWTUtil;
import com.sigma.model.dto.SignInUserDto;
import com.sigma.model.dto.SignInUserResponseDto;
import com.sigma.model.dto.SignUpUserDto;
import com.sigma.model.dto.SignUpUserResponseDto;
import com.sigma.model.entity.User;
import com.sigma.repository.UserRepository;
import com.sigma.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final String SUCCESS = "success";
    private final String FAILURE = "failure";
    private final String CREATED = "user created";
    private final String EXISTED = "user already exists";
    private final String NOT_EXIST = "user doesn't exist";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${jwt-settings.secret-key}")
    private String secret;

    @Value("${jwt-settings.timestamp}")
    private int timestamp;

    @Value("${jwt-settings.issuer}")
    private String issuer;

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
    @Transactional
    public SignUpUserResponseDto createUser(SignUpUserDto signUpDto) {
        log.info("Creating new user {}", signUpDto.getUsername());
        if (userRepository.findByUsername(signUpDto.getUsername()) != null) {
            return new SignUpUserResponseDto(FAILURE, EXISTED);
        }

        User user = new User(signUpDto.getUsername(), passwordEncoder.encode(signUpDto.getPassword()), signUpDto.getRole());
        userRepository.save(user);
        return new SignUpUserResponseDto(SUCCESS, CREATED);
    }

    @Override
    @Transactional
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
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException();
        }
        log.info("Deleting user {}", userRepository.getById(userId).getUsername());
        userRepository.deleteById(userId);
    }

    @Override
    public SignInUserResponseDto login(SignInUserDto signInUserDto) {
        User myUser = userRepository.findByUsername(signInUserDto.getUsername());
        if (myUser == null) {
            return new SignInUserResponseDto(FAILURE, NOT_EXIST);
        }

        if (!passwordEncoder.matches(signInUserDto.getPassword(), myUser.getPassword())) {
            log.info(FAILURE);
            return new SignInUserResponseDto(FAILURE, NOT_EXIST); // TODO: custom exception?
        }

        log.info(SUCCESS);

        String token = JWTUtil.generateJWT(myUser, secret, timestamp, issuer);
        return new SignInUserResponseDto("Bearer", token);
    }
}