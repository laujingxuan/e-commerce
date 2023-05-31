package com.example.user.security;

import com.example.user.entity.User;
import com.example.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // The passwordEncoder() method creates a bean of type BCryptPasswordEncoder and returns it. BCryptPasswordEncoder is one of the built-in implementations of the PasswordEncoder interface provided by Spring Security. It uses the bcrypt hashing algorithm, which is considered secure and suitable for password storage.
        // When a user registers or updates their password, you would use the PasswordEncoder to encode the password before storing it in the database. When authenticating a user, the stored encoded password is compared with the provided password using the PasswordEncoder to determine if they match.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                                .anyRequest().permitAll()
                );
        http.csrf().disable();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserService userService, PasswordEncoder passwordEncoder) {
        return authentication -> {
            String username = authentication.getPrincipal().toString();
            String password = authentication.getCredentials().toString();

            User user = userService.loadUserByUsername(username);

            if (passwordEncoder.matches(password, user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        };
    }
}
