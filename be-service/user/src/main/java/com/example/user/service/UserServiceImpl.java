package com.example.user.service;

import com.example.user.DTO.UserDetailsDTO;
import com.example.user.common.enums.Role;
import com.example.user.dao.UserRepository;
import com.example.user.entity.User;
import com.example.user.modelMapper.UserMapper;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private WebClient webClient;

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, WebClient webClient, UserMapper userMapper){
        this.userRepository = userRepository;
        this.webClient = webClient;
        this.userMapper = userMapper;
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

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public UserDetailsDTO getUserDetails(String pathUuid, String userUuid, String authority) {
        try {
            if (Role.valueOf(authority) != Role.ROLE_ADMIN && !pathUuid.equals(userUuid)){
                throw new IllegalAccessException("User is unauthorized");
            }
            Optional<User> optionalUser = userRepository.findById(userUuid);
            if (!optionalUser.isPresent()){
                throw new UsernameNotFoundException("User info is not found");
            }
            User user = optionalUser.get();
            UserDetailsDTO userDetailsDTO = userMapper.mapToDTO(user);

            return null;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
