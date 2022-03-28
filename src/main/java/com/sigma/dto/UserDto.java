package com.sigma.dto;

import com.sigma.model.Role;
import com.sigma.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private Role role;

    public static UserDto fromUser(User user) {
        return new UserDto()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setRole(user.getRole());
    }

    public User toUser() {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role).build();
    }
}