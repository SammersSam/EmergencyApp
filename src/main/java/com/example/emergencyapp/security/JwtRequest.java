package com.example.emergencyapp.security;

import java.io.Serializable;

public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}