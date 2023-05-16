package com.example.user.controller;

import com.example.user.request.LoginRequest;
import com.example.user.response.AuthenticationResponse;
import com.example.user.utils.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    private JwtTokenService jwtTokenService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtTokenService jwtTokenService){
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
    }

    //The <?> notation used in the ResponseEntity<?> declaration represents a wildcard type or an unknown type. It is used when the specific type of the response body is not known or when the response does not contain any body.
    //The login operation may have different response types based on different conditions. For example, a successful login attempt may return a detailed user profile as the response body, while an unsuccessful login attempt may return an error message. Using <?> allows you to handle different response types without specifying a fixed type in the method signature.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            // Create an Authentication object with the provided username and password
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );
            // Perform the authentication
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Set the authenticated authentication object to the SecurityContext
            // SecurityContextHolder provides a way to store and retrieve the Authentication object, which represents the current user's authentication and authorization information
            // The SecurityContextHolder is designed to propagate the security context across different layers of your application. When a request is received, the security context is typically set at the beginning of the request processing, and it is accessible throughout the request handling chain. This allows you to access the authentication information and perform authorization checks at any point during the request processing
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            String token = jwtTokenService.generateToken(user);

            return ResponseEntity.ok().body(new AuthenticationResponse(token));
        } catch (AuthenticationException e){
            // if authentication failed
            // When you use .build(), it indicates that the response entity does not have a body.
            // If you want to include a response body, you can use .body(...) to set the response body and return the ResponseEntity directly without calling .build()
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/get_test")
    public String testAPI(){
        return "test";
    }
}
