package com.example.action.service;

import com.example.action.DTO.UserActionDTO;
import com.example.action.dao.UserActionRepository;
import com.example.action.entity.UserAction;
import com.example.action.modelMapper.UserActionMapper;
import com.example.shared.enums.Role;
import com.example.shared.exception.CustomUnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ActionServiceImpl implements ActionService {

    private Logger logger = LoggerFactory.getLogger(ActionServiceImpl.class);

    private UserActionMapper userActionMapper;

    private UserActionRepository userActionRepository;

    @Autowired
    public ActionServiceImpl(UserActionMapper userActionMapper, UserActionRepository userActionRepository) {
        this.userActionMapper = userActionMapper;
        this.userActionRepository = userActionRepository;
    }

    @Override
    public UserActionDTO create(UserActionDTO userActionDTO) {
        UserAction userAction = userActionMapper.mapToEntity(userActionDTO);
        userAction.setActionTime(Timestamp.valueOf(LocalDateTime.now()));
        UserAction createdAction = userActionRepository.save(userAction);
        userActionDTO = userActionMapper.mapToDTO(createdAction);
        return userActionDTO;
    }

    @Override
    public List<UserActionDTO> getUserActionList(String pathUuid, String userUuid, String authority) {
        if (Role.valueOf(authority) != Role.ROLE_ADMIN && !pathUuid.equals(userUuid)) {
            throw new CustomUnauthorizedException("Unauthorized user");
        }

        List<UserAction> userActionList = userActionRepository.findByUserUuid(pathUuid);
        List<UserActionDTO> dtoList = new ArrayList<>();
        for (UserAction userAction : userActionList) {
            UserActionDTO userActionDTO = userActionMapper.mapToDTO(userAction);
            dtoList.add(userActionDTO);
        }
        return dtoList;
    }
}
