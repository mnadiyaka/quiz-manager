package com.sigma.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int userid;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;
//    private Role role; //enum
}
