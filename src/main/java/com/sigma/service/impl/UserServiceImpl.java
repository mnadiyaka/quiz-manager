package com.sigma.service.impl;

import com.sigma.configuration.auth.JWTUtil;
import com.sigma.model.dto.SignInUserDto;
import com.sigma.model.dto.SignInUserResponseDto;
import com.sigma.model.dto.SignUpUserDto;
import com.sigma.model.dto.SignUpUserResponseDto;
import com.sigma.model.dto.UserDto;
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
import java.util.Objects;
import java.util.Optional;

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
    public List<UserDto> getAllUsers() {
        log.info("Getting list of users");
        return userRepository.findAll().stream().map(UserDto::fromUser).toList();
    }

    @Override
    public User findUserById(final Long userId) {
        log.info("Searching for user with id {}", userId);
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id = " + userId + " not found"));
    }

    @Override
    public User findUserByUsername(final String username) {
        log.info("Searching for user with username {}", username);

        return Optional.ofNullable(userRepository.findByUsername(username)).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    @Transactional
    public SignUpUserResponseDto createUser(final SignUpUserDto signUpDto) {
        log.info("Creating new user {}", signUpDto.getUsername());
        if (!Objects.isNull(userRepository.findByUsername(signUpDto.getUsername()))) {
            return new SignUpUserResponseDto(FAILURE, EXISTED);
        }

        final User user = new User(signUpDto.getUsername(), passwordEncoder.encode(signUpDto.getPassword()), signUpDto.getRole());
        userRepository.save(user);
        return new SignUpUserResponseDto(SUCCESS, CREATED);
    }

    @Override
    @Transactional
    public void updateUser(final User updatedUser, final Long userId) {
        final User oldUser = findUserById(userId);
        log.info("Updating user {}", oldUser.getUsername());
        oldUser.setUsername(updatedUser.getUsername());
        oldUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        oldUser.setRole(updatedUser.getRole());
        oldUser.setAdminLocation(updatedUser.getAdminLocation());
        userRepository.save(oldUser);
    }

    @Override
    @Transactional
    public void deleteUser(final Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException();
        }
        log.info("Deleting user {}", userRepository.getById(userId).getUsername());
        userRepository.deleteById(userId);
    }

    @Override
    public SignInUserResponseDto login(final SignInUserDto signInUserDto) {
        final User myUser = userRepository.findByUsername(signInUserDto.getUsername());
        if (Objects.isNull(myUser)) {
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