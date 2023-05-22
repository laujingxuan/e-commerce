package com.example.action.modelMapper;

import com.example.action.DTO.UserActionDTO;
import com.example.action.entity.UserAction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class UserActionMapper {
    private ModelMapper modelMapper;

    @Autowired
    public UserActionMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public UserAction mapToEntity(UserActionDTO userActionDTO){
        UserAction userAction = modelMapper.map(userActionDTO, UserAction.class);
        if (userActionDTO.getActionTime() != null){
            userAction.setActionTime(userActionDTO.getActionTime());
        } else {
            userAction.setActionTime(Timestamp.valueOf(LocalDateTime.now()));
        }
        return userAction;
    }

    public UserActionDTO mapToDTO(UserAction userAction){
        UserActionDTO userActionDTO = modelMapper.map(userAction, UserActionDTO.class);
        return userActionDTO;
    }
}
