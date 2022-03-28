package com.sigma.controller;

import com.sigma.dto.UserDto;
import com.sigma.model.User;
import com.sigma.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        return userService.getAllUsers().stream().map(UserDto::fromUser).toList();
    }

    @GetMapping("/")
    public String index() {
        log.info("Hello world printing....");
        return "Hello World";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute UserDto userDto) {

        User user = new User();
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());

        userService.createUser(user);

        return "register-success";
    }

    @PostMapping("/users/login")
    public String loginUser(@RequestBody User user) {
        List<User> users = userService.getAllUsers();

        for (User other : users) {
            if (other.equals(user)) {
                return "logged in";
            }
        }

        return "can't log in";
    }

    @DeleteMapping("/users/{user_d}/delete")
    public String deleteUser(@PathVariable("user_id") Long userId) {
        userService.deleteUser(userId);
        return "deleted user";
    }
//    @GetMapping("/admin")
//    public String admin_in(){
//        log.info("Quizzes....");
//        return "quizzes";
//    }
//    @GetMapping("/captain")
//    public String capt_in(){
//        log.info("team management....");
//        return "team";
//    }
}