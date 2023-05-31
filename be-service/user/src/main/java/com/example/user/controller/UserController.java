package com.example.user.controller;

import com.example.user.DTO.UserDetailsDTO;
import com.example.user.common.UserJwtTokenService;
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

    private UserJwtTokenService userJwtTokenService;

    @Autowired
    public UserController(UserService userService, UserJwtTokenService userJwtTokenService) {
        this.userService = userService;
        this.userJwtTokenService = userJwtTokenService;
    }

    @GetMapping("/{pathUuid}")
    public ResponseEntity<?> getUserDetails(@PathVariable String pathUuid, HttpServletRequest httpServletRequest) {
        String jwtToken = userJwtTokenService.extractJwtTokenFromRequest(httpServletRequest);
        if (!userJwtTokenService.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userUuid = userJwtTokenService.extractUserUuid(jwtToken);
        String authority = userJwtTokenService.extractAuthority(jwtToken);

        UserDetailsDTO userDetailsDTO = userService.getUserDetails(pathUuid, userUuid, authority, jwtToken);
        if (userDetailsDTO != null) {
            return ResponseEntity.ok().body(userDetailsDTO);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/validity/{pathUuid}")
    public ResponseEntity<?> isUserUuidValid(@PathVariable String pathUuid, HttpServletRequest httpServletRequest) {
        String jwtToken = userJwtTokenService.extractJwtTokenFromRequest(httpServletRequest);
        if (!userJwtTokenService.validateToken(jwtToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userUuid = userJwtTokenService.extractUserUuid(jwtToken);
        String authority = userJwtTokenService.extractAuthority(jwtToken);

        if (!userService.isUserUuidValid(pathUuid, userUuid, authority)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }
}
