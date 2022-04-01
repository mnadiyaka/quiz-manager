package com.sigma.controller;

import com.sigma.JWTUtil;
import com.sigma.dto.SignInUserDto;
import com.sigma.dto.SignInUserResponseDto;
import com.sigma.dto.SignUpUserDto;
import com.sigma.dto.SignUpUserResponseDto;
import com.sigma.dto.UserDto;
import com.sigma.model.User;
import com.sigma.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final String SUCCESS = "success";
    private final String FAILURE = "failure";

    @Autowired
    private UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    @Value("${jwt-settings.secret-key}")
    private String secret;

    @Autowired
    @Value("${jwt-settings.timestamp}")
    private int timestamp;

    @Autowired
    @Value("${jwt-settings.issuer}")
    private String issuer;

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getAllUsers().stream().map(UserDto::fromUser).toList();
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
        User myUser = userService.findUserByUsername(user.getUsername());

        if (passwordEncoder.matches(user.getPassword(), myUser.getPassword())) {
            log.info(SUCCESS);

            String token = JWTUtil.generateJWT(myUser, secret, timestamp, issuer); //TODO: issuer?
            return new SignInUserResponseDto("Bearer", token);
        }
        log.info(FAILURE);
        throw new AuthenticationException(FAILURE);
    }

    @DeleteMapping("/users/{user_id}/delete")
    public String deleteUser(@PathVariable("user_id") Long userId) {
        userService.deleteUser(userId);
        return "deleted user";
    }

    @GetMapping("/admin")
    public String admin() {
        log.info("Quizzes....");
        return "quizzes";
    }

    @GetMapping("/captain")
    public String captain() {
        log.info("team management....");
        return "team";
    }
}