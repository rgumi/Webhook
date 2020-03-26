package com.telekom.whatsapp.auth.jwt.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.telekom.whatsapp.auth.jwt.JwtUtils;
import com.telekom.whatsapp.auth.jwt.UserDetailsImpl;
import com.telekom.whatsapp.auth.jwt.services.MyUserDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  private MyUserDetailsService myUserDetailsService;

  @Autowired
  private JwtUtils jwtUtils;
  
  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
        String jwt = jwtUtils.parseJwtFromRequest(request);

        if (jwt != null &&  jwtUtils.validateJwtToken(jwt) && SecurityContextHolder.getContext().getAuthentication() == null) {

          UserDetailsImpl userDeatils = myUserDetailsService.loadUserByUsername(jwtUtils.extractUsername(jwt));
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDeatils, null, userDeatils.getAuthorities());

          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }

    } catch (Exception e) {
      logger.error("Cannot set authentication: {}", e);
    }

    filterChain.doFilter(request, response);
  }
}