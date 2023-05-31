package com.example.user.common;

import com.example.shared.enums.Role;
import com.example.shared.utils.JwtTokenService;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserJwtTokenService extends JwtTokenService {

    private UserService userService;

    @Autowired
    public UserJwtTokenService(WebClient webClient, UserService userService) {
        super(webClient);
        this.userService = userService;
    }

    @Override
    public Boolean validateToken(String token) {
        try {
            String authority = extractAuthority(token);
            // IllegalArgumentException will be thrown when value not existed in enum
            Role.valueOf(authority);
            String userUuid = extractUserUuid(token);
            return userService.isUserUuidValid(userUuid, userUuid, authority) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
