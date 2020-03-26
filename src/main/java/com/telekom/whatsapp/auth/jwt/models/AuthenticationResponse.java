package com.telekom.whatsapp.auth.jwt.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationResponse {
    
    private final String jwt;
    private final Date expirationDate;

    public AuthenticationResponse(String jwt, Date expirationDate) {
        this.jwt = jwt;
        this.expirationDate = expirationDate;
    }

    @JsonProperty("jwt_token")
    public String getJwt() {
        return jwt;
    }
    @JsonProperty("expiration_date")
    public Date getExpirationDate() {
        return expirationDate;
    }
}