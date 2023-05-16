package com.example.user.serviceImpl;

import com.example.user.dao.UserRepository;
import com.example.user.entity.User;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e){
            if (e.getMessage().contains("email")){
                System.out.println("email constraints not met");
            } else if (e.getMessage().contains("username")){
                System.out.println("username constraints not met");
            } else {
                System.out.println(e.getMessage());
            }
        }
    }
}
