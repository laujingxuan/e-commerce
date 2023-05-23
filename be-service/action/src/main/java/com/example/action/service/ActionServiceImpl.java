package com.example.action.service;

import com.example.action.DTO.UserActionDTO;
import com.example.action.dao.UserActionRepository;
import com.example.action.entity.UserAction;
import com.example.action.modelMapper.UserActionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Transactional
public class ActionServiceImpl implements ActionService{

    private UserActionMapper userActionMapper;

    private UserActionRepository userActionRepository;

    @Autowired
    public ActionServiceImpl(UserActionMapper userActionMapper, UserActionRepository userActionRepository){
        this.userActionMapper = userActionMapper;
        this.userActionRepository = userActionRepository;
    }

    @Override
    public UserActionDTO create(UserActionDTO userActionDTO) {
        try{
            UserAction userAction = userActionMapper.mapToEntity(userActionDTO);
            userAction.setActionTime(Timestamp.valueOf(LocalDateTime.now()));
            UserAction createdAction = userActionRepository.save(userAction);
            userActionDTO = userActionMapper.mapToDTO(createdAction);
            return userActionDTO;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
