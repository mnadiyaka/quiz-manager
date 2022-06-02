package com.sigma.service;

import com.sigma.configuration.auth.JWTUtil;
import com.sigma.model.dto.SignInUserDto;
import com.sigma.model.dto.SignInUserResponseDto;
import com.sigma.model.dto.SignUpUserDto;
import com.sigma.model.dto.SignUpUserResponseDto;
import com.sigma.model.dto.UserDto;
import com.sigma.model.entity.Role;
import com.sigma.model.entity.User;
import com.sigma.repository.UserRepository;
import com.sigma.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
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
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private UserService userService;

    private final PasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);

    @BeforeEach
    void setUp() {
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
    public void createUserTest_WithUser_ThenReturnResponseDto() {
        SignUpUserResponseDto expected = new SignUpUserResponseDto("success", "user created");

        SignUpUserDto signUpUserDto = new SignUpUserDto();
        signUpUserDto.setUsername("new");
        signUpUserDto.setPassword("new");

        when(passwordEncoder.encode(anyString())).thenReturn(signUpUserDto.getPassword());
        User user = new User(signUpUserDto.getUsername(), passwordEncoder.encode(signUpUserDto.getPassword()), Role.CAPTAIN);
        when(userRepository.save(user)).thenReturn(user);

        SignUpUserResponseDto actual = userService.createUser(signUpUserDto);
        Assertions.assertEquals(expected, actual);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void updateUserTest_WithUpdatedUserAndId_ThenReturnUpdatedUser() {
        User user = new User();
        user.setId(1L);
        user.setPassword("pas");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User oldUser = userService.findUserById(1L);
        oldUser.setUsername("new");

        when(userRepository.save(oldUser)).thenReturn(oldUser);

        User actual = userService.updateUser(oldUser, 1L);
        Assertions.assertEquals(oldUser, actual);
        verify(userRepository, times(1)).save(oldUser);
    }

    @Test
    public void deleteUser_WithCorrectUserId() {
        User expected = new User("j", "j", Role.CAPTAIN);
        expected.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(expected));

        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    public void deleteUser_WithIncorrectUserId_ThenThrowException() {
        User expected = new User();
        expected.setId(1L);
        when(userRepository.findById(2L)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(2L));
        verify(userRepository, times(0)).delete(expected);
    }

    @Test
    public void getAllUsers_WithUserId_ThenReturnList() {
        List<User> users = new ArrayList<>();
        users.add(new User("j", "j", Role.CAPTAIN));
        users.add(new User("jj", "j", Role.CAPTAIN));
        users.add(new User("jjj", "j", Role.CAPTAIN));
        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> actual = userService.getAllUsers();
        Assertions.assertEquals(users.stream().map(UserDto::fromUser).collect(Collectors.toList()), actual);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void login_WithCorrectSignInUserDto_ThenReturnPositiveResponse() {
        User user = new User();
        user.setId(1L);
        user.setUsername("name");
        when(passwordEncoder.encode("ppp")).thenReturn("ppp");
        user.setPassword(passwordEncoder.encode("ppp"));
        user.setRole(Role.CAPTAIN);
        SignInUserDto signInUserDto = new SignInUserDto();
        signInUserDto.setUsername("name");
        signInUserDto.setPassword("ppp");
        when(userRepository.findByUsername(signInUserDto.getUsername())).thenReturn(user);
        when(passwordEncoder.matches("ppp", "ppp")).thenReturn(Boolean.TRUE);
        MockedStatic<JWTUtil> jwtUtilMockedStatic = mockStatic(JWTUtil.class);
        jwtUtilMockedStatic.when(() -> JWTUtil.generateJWT(user, "secret", 0, "issuer"))
                .thenReturn("token");

        Assertions.assertNotEquals(new SignInUserResponseDto("failure", "user doesn't exist"), userService.login(signInUserDto));
        verify(userRepository, times(1)).findByUsername(signInUserDto.getUsername());
    }

    @Test
    public void login_WithInCorrectPassword_ThenReturnNegativeResponse() {
        User user = new User();
        user.setId(1L);
        user.setUsername("name");
        user.setPassword(passwordEncoder.encode("qqq"));
        SignInUserDto signInUserDto = new SignInUserDto();
        signInUserDto.setUsername("name");
        signInUserDto.setPassword("ppp");
        when(userRepository.findByUsername(signInUserDto.getUsername())).thenReturn(user);

        Assertions.assertEquals(new SignInUserResponseDto("failure", "user doesn't exist"), userService.login(signInUserDto));
    }

    @Test
    public void login_WithInCorrectUsername_ThenReturnNegativeResponse() {
        User user = new User();
        user.setId(1L);
        user.setUsername("name1");
        user.setPassword(passwordEncoder.encode("ppp"));
        SignInUserDto signInUserDto = new SignInUserDto();
        signInUserDto.setUsername("name");
        signInUserDto.setPassword("ppp");
        when(userRepository.findByUsername(signInUserDto.getUsername())).thenReturn(null);

        Assertions.assertEquals(new SignInUserResponseDto("failure", "user doesn't exist"), userService.login(signInUserDto));
    }

    @Test
    public void changeAccRole_WithUserId_ThenUpdateUser() {
        User expected = new User();
        expected.setId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        when(userRepository.save(expected)).thenReturn(expected);
        userService.changeAccRole(1L);
        User admin = userService.findUserById(1L);
        Assertions.assertEquals(Role.ADMIN, admin.getRole());
        verify(userRepository, times(1)).save(expected);
    }
}