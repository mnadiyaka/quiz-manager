package com.sigma.model.dto;

import com.sigma.model.entity.Role;
import com.sigma.model.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class UserDto {

    private Long id;

    @NotNull
    private String username;

    private Role role;

    public static UserDto fromUser(User user) {
        return new UserDto()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setRole(user.getRole());
    }
}