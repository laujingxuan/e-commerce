package com.example.user.testDataInit;

import com.example.user.entity.User;
import com.example.user.common.enums.Role;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestUsersInit implements ApplicationRunner {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public TestUsersInit(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // the run method will be automatically invoked during application startup
    @Override
    public void run(ApplicationArguments args) throws Exception{
        //Create test users
        User adminUser = new User("admin", passwordEncoder.encode("admin123"), "admin@hotmail.com", Role.ROLE_ADMIN);
        userService.saveUser(adminUser);

        User normalUser = new User("user", passwordEncoder.encode("user123"), "user@hotmail.com", Role.ROLE_USER);
        userService.saveUser(normalUser);
    }
}
