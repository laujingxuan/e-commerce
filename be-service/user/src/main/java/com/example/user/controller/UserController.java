package com.example.user.controller;

import com.example.user.DTO.UserDetailsDTO;
import com.example.user.common.JwtTokenService;
import com.example.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    private JwtTokenService jwtTokenService;

    @Autowired
    public UserController(UserService userService, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping("/{pathUuid}")
    public ResponseEntity<?> getUserDetails(@PathVariable String pathUuid, HttpServletRequest httpServletRequest) {
        String jwtToken = jwtTokenService.extractJwtTokenFromRequest(httpServletRequest);
        if (!jwtTokenService.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userUuid = jwtTokenService.extractUserUuid(jwtToken);
        String authority = jwtTokenService.extractAuthority(jwtToken);

        UserDetailsDTO userDetailsDTO = userService.getUserDetails(pathUuid, userUuid, authority, jwtToken);
        if (userDetailsDTO != null) {
            return ResponseEntity.ok().body(userDetailsDTO);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/validity/{pathUuid}")
    public ResponseEntity<?> isUserUuidValid(@PathVariable String pathUuid, HttpServletRequest httpServletRequest) {
        String jwtToken = jwtTokenService.extractJwtTokenFromRequest(httpServletRequest);
        if (!jwtTokenService.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userUuid = jwtTokenService.extractUserUuid(jwtToken);
        String authority = jwtTokenService.extractAuthority(jwtToken);

        if (!userService.isUserUuidValid(pathUuid, userUuid, authority)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }
}
