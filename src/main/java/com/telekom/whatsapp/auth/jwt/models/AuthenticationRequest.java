package com.telekom.whatsapp.auth.jwt.models;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AuthenticationRequest {
    
    @NotNull(message = "username is required")
    @JsonAlias({"username", "userName"})
    private String userName;
    
    @NotNull(message = "password is required")
    @JsonAlias({"password"})
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}