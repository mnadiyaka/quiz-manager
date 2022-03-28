package com.sigma.service;

import com.sigma.model.Role;
import com.sigma.model.User;
import com.sigma.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void findUserById() {
        User user = userService.findUserById(1L);

        Assertions.assertEquals(user.getUsername(), "adminn");
    }

    @Test
    @Transactional
    public void createUserTest() {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("12345");
        user.setRole(Role.CAPTAIN);

        userService.createUser(user);

        Assertions.assertEquals(user, userService.findUserById(user.getId()));
    }

    @Test
    @Transactional
    public void updateUserTest() {
        User user = userService.findUserById(1L);
        user.setRole(Role.CAPTAIN);

        userService.updateUser(user, user.getId());
        Assertions.assertEquals(user, userService.findUserById(1L));
    }

    @Test
    @Transactional
    public void deleteUser() {
        int expected = userService.getAllUsers().size();
        userService.deleteUser(1L);
        int actual = userService.getAllUsers().size();
        Assertions.assertEquals(expected - 1, actual);
    }

    @Test
    @Transactional
    public void getAllUsers() {
        int expected = userRepository.findAll().size();

        User user = new User();
        user.setUsername("user1");
        user.setPassword("12345");
        user.setRole(Role.CAPTAIN);

        userService.createUser(user);

        int actual = userService.getAllUsers().size();

        Assertions.assertEquals(expected + 1, actual);
    }
}