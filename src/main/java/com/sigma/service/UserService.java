package com.sigma.service;

import com.sigma.model.dto.SignInUserDto;
import com.sigma.model.dto.SignInUserResponseDto;
import com.sigma.model.dto.SignUpUserDto;
import com.sigma.model.dto.SignUpUserResponseDto;
import com.sigma.model.dto.UserDto;
import com.sigma.model.entity.User;
import org.apache.tomcat.websocket.AuthenticationException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface UserService {
    /**
     * Finds all users
     *
     * @return List of users
     */
    List<UserDto> getAllUsers();

    /**
     * Finds user by existing id
     *
     * @param userId User's id
     * @return Found user
     * @throws EntityNotFoundException If User's id does not exist
     */
    User findUserById(Long userId);

    /**
     * Finds user by username
     *
     * @param username User's name
     * @return Found User
     * @throws EntityNotFoundException If User's name does not exist
     */
    User findUserByUsername(String username);

    /**
     * Creates new User with entered UserDto (username and password), by default role is CAPTAIN
     *
     * @param signUpDto Entered data
     * @return positive SignUpUserResponseDto, if successfully created
     * @return negative SignUpUserResponseDto, if user already exist with this name
     */
    SignUpUserResponseDto createUser(SignUpUserDto signUpDto);

    /**
     * Updates existing user by id with entered UserDto (username, password), you can not change role here
     *
     * @param updatedUser Updated data
     * @param userId      User's id
     * @return Updated user
     * @throws EntityNotFoundException If User's id does not exist
     */
    User updateUser(User updatedUser, Long userId);

    /**
     * Deletes user by id
     *
     * @param userId User's id
     * @throws EntityNotFoundException If User's id does not exist
     */
    void deleteUser(Long userId);

    /**
     * Logs into the system with existing user's login and password, entered in SignInUserDto
     *
     * @param signInUserDto data for Logging in
     * @return positive SignInUserResponseDto if successfully logged in
     * @return negative SignInUserResponseDto if wrong data entered
     * @throws EntityNotFoundException If User's id does not exist
     */
    SignInUserResponseDto login(SignInUserDto signInUserDto);

    /**
     * Updates User from Captain to Admin Role
     *
     * @param userId User's id
     * @throws EntityNotFoundException If User's id does not exist
     */
    void changeAccRole(final Long userId);
}