package com.sigma.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "Enter username")
    @Size(min = 3, max = 20)
    @Column(name = "user_name")
    private String username;

    @NotBlank(message = "Enter password")
    @Column(name = "password")
    @Size(min = 5, max = 20)
    private String password;

    @NotBlank(message = "Enter role")
    @Column(name = "role")
    private Role role;

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @ToString.Exclude
    @JoinTable(name = "admin_location", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"))
    private List<Location> adminLocation;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}