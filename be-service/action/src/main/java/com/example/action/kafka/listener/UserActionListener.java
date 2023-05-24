package com.example.action.kafka.listener;

import com.example.action.DTO.UserActionDTO;
import com.example.action.common.enums.ActionOnItem;
import com.example.action.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserActionListener {

    @Autowired
    private ActionService actionService;

    public UserActionListener(ActionService actionService){
        this.actionService = actionService;
    }

    @KafkaListener(topics = "user-action")
    public void createActionListener(String kafkaMsg) {
        try {
            String[] parts = kafkaMsg.split(";");
            if (parts.length != 3){
                throw new IllegalArgumentException();
            }
            UserActionDTO userActionDTO = new UserActionDTO();
            userActionDTO.setActionOnItem(ActionOnItem.valueOf(parts[0]));
            userActionDTO.setItemId(Integer.parseInt(parts[1]));
            userActionDTO.setUserUuid(parts[2]);
            actionService.create(userActionDTO);
        } catch (Exception e){
            System.out.println("Invalid kafka msg: " + kafkaMsg);
        }
    }
}
