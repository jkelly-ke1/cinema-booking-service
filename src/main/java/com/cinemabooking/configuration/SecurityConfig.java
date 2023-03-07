package com.cinemabooking.configuration;


import com.cinemabooking.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    private final EmployeeService employeeService;

    @Autowired
    public SecurityConfig(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**").hasRole("ADMIN")
                .antMatchers("/auth/signup").permitAll()
                .anyRequest().hasAnyRole("EMPLOYEE", "ADMIN")
                .and()
                .httpBasic();

        http.cors().disable().csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(employeeService);
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }

}
