package com.example.user.service;

import com.example.user.dao.UserRepository;
import com.example.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

// @ExtendWith is JUnit 5 annotation
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testLoadUserByUsername_UserFound(){
        User testUser = new User("John", "password", "email");
        when(userRepositoryMock.findByUsername(testUser.getUsername())).thenReturn(testUser);
        // assertEquals 3rd param, message is optional. To display custom error message
        assertEquals(testUser, userService.loadUserByUsername(testUser.getUsername()), "User returned not match");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound(){
        User testUser = new User("John", "password", "email");
        when(userRepositoryMock.findByUsername(testUser.getUsername())).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(testUser.getUsername());
        });
        assertEquals(exception.getMessage(), "User not found");
    }
}
