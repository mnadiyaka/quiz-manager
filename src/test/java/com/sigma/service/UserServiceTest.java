package com.sigma.service;

import com.sigma.configuration.auth.TokenAuth;
import com.sigma.model.dto.SignInUserDto;
import com.sigma.model.dto.SignUpUserDto;
import com.sigma.model.dto.SignUpUserResponseDto;
import com.sigma.model.dto.TeamDto;
import com.sigma.model.dto.UserDto;
import com.sigma.model.entity.Role;
import com.sigma.model.entity.Team;
import com.sigma.model.entity.User;
import com.sigma.repository.ParticipantRepository;
import com.sigma.repository.TeamRepository;
import com.sigma.repository.UserRepository;
import com.sigma.service.impl.ParticipantServiceImpl;
import com.sigma.service.impl.TeamServiceImpl;
import com.sigma.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserRepository userRepository;

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    public void findUserById_WithExistingId_ThenReturnUser() {
        final User expectedResult = new User();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expectedResult));
        final User result = userService.findUserById(anyLong());

        Assertions.assertEquals(expectedResult, result);
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void findUserById_WithNonExistingId_ThenThrowException() {
        when(userRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findUserById(anyLong()));
    }

    @Test
    public void createUserTest_WithUser_ThenReturnNewUser() {//TODO: SMTH With Encode
        SignUpUserResponseDto expected = new SignUpUserResponseDto("success", "user created");

        SignUpUserDto signUpUserDto = new SignUpUserDto();
        signUpUserDto.setUsername("new");
        signUpUserDto.setPassword("new");
        signUpUserDto.setRole(Role.CAPTAIN);

        User user = new User(signUpUserDto.getUsername(), passwordEncoder.encode(signUpUserDto.getPassword()), Role.CAPTAIN);
        when(this.userRepository.save(user)).thenReturn(user);

        SignUpUserResponseDto actual = userService.createUser(signUpUserDto);
        Assertions.assertEquals(expected, actual);
        verify(this.userRepository, times(1)).save(user);
    }

    @Test
    public void updateUserTest_WithUpdatedUserAndId_ThenReturnUpdatedUser() {
        User team = new User();
        team.setId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(team));

        User oldUser = userService.findUserById(1L);
        oldUser.setUsername("new");

        when(userRepository.save(oldUser)).thenReturn(oldUser);

        User actual = userService.updateUser(oldUser, 1L);
        Assertions.assertEquals(oldUser, actual);
        verify(userRepository, times(1)).save(oldUser);
    }

    @Test
    public void deleteUser_WithCorrectCaptain() {
        User expected = new User();
        expected.setId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        userService.deleteUser(1L);
        verify(userRepository).delete(expected);
    }

    @Test
    public void deleteUser_WithIncorrectCaptain_ThenThrowException() {
        User expected = new User();
        expected.setId(1L);
        when(userRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(anyLong()));
        verify(userRepository, times(0)).delete(expected);
    }

    @Test
    public void getAllUsers_WithUserId_ThenReturnList() {
        List<User> teams = new ArrayList<>();
        teams.add(new User());
        teams.add(new User());
        teams.add(new User());
        when(userRepository.findAll()).thenReturn(teams);

        List<UserDto> actual = userService.getAllUsers();
        Assertions.assertEquals(teams.stream().map(UserDto::fromUser).collect(Collectors.toList()), actual);
        verify(userRepository, times(1)).findAll();
    }
}