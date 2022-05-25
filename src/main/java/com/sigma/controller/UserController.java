package com.sigma.controller;

import com.sigma.model.dto.SignInUserDto;
import com.sigma.model.dto.SignInUserResponseDto;
import com.sigma.model.dto.SignUpUserDto;
import com.sigma.model.dto.SignUpUserResponseDto;
import com.sigma.model.dto.UserDto;
import com.sigma.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/")
    public String index() {
        log.info("Hello world printing....");
        return "Hello World";
    }

    @PostMapping("/signUp")
    public SignUpUserResponseDto signUp(@RequestBody SignUpUserDto signUpDto) {
        return userService.createUser(signUpDto);
    }

    @PostMapping("/login")
    public SignInUserResponseDto loginUser(@RequestBody SignInUserDto user) throws AuthenticationException {
        return userService.login(user);
    }

    @DeleteMapping("/users/{user_id}/delete")
    public String deleteUser(@PathVariable("user_id") Long userId) {
        userService.deleteUser(userId);
        return "deleted user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("change/{user_id}")
    public String changeAccRole(@PathVariable("user_id") final Long userId) {
        userService.changeAccRole(userId);
        return "changed";
    }
}
