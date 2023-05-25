package com.example.action.kafka.listener;

import com.example.action.DTO.UserActionDTO;
import com.example.action.common.enums.ActionOnItem;
import com.example.action.service.ActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserActionListener {

    private Logger logger = LoggerFactory.getLogger(UserActionListener.class);

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
            logger.warn("Invalid kafka msg: {}" + kafkaMsg);
        }
    }
}
