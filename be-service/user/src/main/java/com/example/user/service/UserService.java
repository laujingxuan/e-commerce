package com.example.user.service;

import com.example.user.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    void saveUser(User user);

    User loadUserByUsername(String username) throws UsernameNotFoundException;
}
