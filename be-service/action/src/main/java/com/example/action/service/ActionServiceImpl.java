package com.example.action.service;

import com.example.action.modelMapper.UserActionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActionServiceImpl implements ActionService{

    private UserActionMapper userActionMapper;

    @Autowired
    public ActionServiceImpl(UserActionMapper userActionMapper){
        this.userActionMapper = userActionMapper;
    }
}
