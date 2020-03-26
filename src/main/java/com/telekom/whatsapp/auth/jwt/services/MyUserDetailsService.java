package com.telekom.whatsapp.auth.jwt.services;

import com.telekom.whatsapp.auth.jwt.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    // my single user
    private String id = "adminId";
    @Value("${webhook.api.security.user.name}")
    private String name;
    @Value("${webhook.api.security.user.password}")
    private String password;
    @Value("${webhook.api.security.user.email}")
    private String email;

    @Override
    public UserDetailsImpl loadUserByUsername(String userName) throws UsernameNotFoundException {

        return new UserDetailsImpl(id, name, email, password);
    }

}
