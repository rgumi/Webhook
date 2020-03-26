package com.telekom.whatsapp.auth.jwt.controller;

import javax.validation.Valid;

import com.telekom.whatsapp.auth.jwt.JwtUtils;
import com.telekom.whatsapp.auth.jwt.UserDetailsImpl;
import com.telekom.whatsapp.auth.jwt.models.AuthenticationRequest;
import com.telekom.whatsapp.auth.jwt.models.AuthenticationResponse;
import com.telekom.whatsapp.auth.jwt.services.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path="${webhook.api.prefix}")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping(path = "/auth")
    ResponseEntity<Object> createAuthenticationToken(@RequestBody @Valid AuthenticationRequest authReq) throws Exception {
        String userName = authReq.getUserName();
        String password = authReq.getPassword();

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password)
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetailsImpl userDeails = myUserDetailsService.loadUserByUsername(userName);

        final String jwt = jwtUtils.generateToken(userDeails); 

        return ResponseEntity.ok(new AuthenticationResponse(jwt, jwtUtils.extractExpiration(jwt)));
    }
}