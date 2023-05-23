package com.example.action.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserActionListener {
    @KafkaListener(topics = "user-action")
    public void createActionListener(String kafkaMsg) {
        System.out.println("test");
        System.out.println(kafkaMsg);
    }
}
