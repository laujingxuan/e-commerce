package com.example.user.service;

import com.example.user.DTO.UserDetailsDTO;
import com.example.user.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    void saveUser(User user);

    User loadUserByUsername(String username) throws UsernameNotFoundException;

    UserDetailsDTO getUserDetails(String pathUuid, String userUuid, String authority, String jwtToken);

    boolean isUserUuidValid(String pathUuid, String userUuid, String authority, String jwtToken);
}
