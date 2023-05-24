package com.example.action.service;

import com.example.action.DTO.UserActionDTO;

import java.util.List;

public interface ActionService {
    UserActionDTO create(UserActionDTO userActionDTO);

    List<UserActionDTO> getUserActionList(String pathUuid, String userUuid, String authority);
}
