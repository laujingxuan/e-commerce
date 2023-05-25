package com.example.action.kafka;

import com.example.action.common.enums.ActionOnItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ActionKafkaUtils {

    private Logger logger = LoggerFactory.getLogger(ActionKafkaUtils.class);

    private KafkaTemplate kafkaTemplate;

    private String topicName;

    @Autowired
    public ActionKafkaUtils(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = "user-action";
    }

    // The send API returns a CompletableFuture object. If we want to block the sending thread and get the result about the sent message, we can call the get API of the CompletableFuture object. The thread will wait for the result, but it will slow down the producer.
    // it's better to handle the results asynchronously so that the subsequent messages do not wait for the result of the previous message
    public void sendMessage(ActionOnItem actionOnItem, int itemId, String userUuid) {
        String kafkaMsg = actionOnItem.name() + ";" + itemId + ";" + userUuid;
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, kafkaMsg);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Sent message=[" + kafkaMsg +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                logger.warn("Unable to send message=[" +
                        kafkaMsg + "] due to : " + ex.getMessage());
            }
        });
    }
}
