package com.example.user.modelMapper;

import com.example.user.DTO.UserDetailsDTO;
import com.example.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public User mapToEntity(UserDetailsDTO userDetailsDTO){
        User user = modelMapper.map(userDetailsDTO, User.class);
        return user;
    }

    public UserDetailsDTO mapToDTO(User user){
        UserDetailsDTO userDetailsDTO = modelMapper.map(user, UserDetailsDTO.class);
        return userDetailsDTO;
    }
}
