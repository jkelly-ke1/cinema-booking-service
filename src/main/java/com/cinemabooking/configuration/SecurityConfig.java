package com.cinemabooking.configuration;


import com.cinemabooking.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final EmployeeService employeeService;

    @Autowired
    public SecurityConfig(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //test auth configuration
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/seat").hasRole("EMPLOYEE")
                .and()
                .authorizeRequests().antMatchers("/auth/**", "/user/**").permitAll()
                .and()
                .httpBasic();

        http.cors().disable().csrf().disable();
        http.headers().frameOptions().disable();

    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
