package com.sigma.model;

public enum Role {
    ADMIN ("Admin"), CAPTAIN("Captain");

    private String role;
    Role(String role){
        this.role = role;
    }
}
