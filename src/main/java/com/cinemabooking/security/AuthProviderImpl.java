package com.cinemabooking.security;


import com.cinemabooking.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final EmployeeService employeeService;

    @Autowired
    public AuthProviderImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        UserDetails employeeDetails = employeeService.loadUserByUsername(username);

        String password = authentication.getCredentials().toString();

        if (!password.equals(employeeDetails.getPassword()))
            throw new BadCredentialsException("Bad password");

        // third argument uses for roles
        return new UsernamePasswordAuthenticationToken(employeeDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
