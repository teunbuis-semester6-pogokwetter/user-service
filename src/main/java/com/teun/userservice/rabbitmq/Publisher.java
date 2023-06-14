package com.teun.userservice.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Publisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(Publisher.class);

    public void sendForDeletion(Long userId){
        try {
            String toDelete = objectMapper.writeValueAsString(userId);
            rabbitTemplate.convertAndSend(MQConfig.EXCHANGE_USER_DELETE, MQConfig.ROUTINGKEY_USER_DELETE, toDelete);
            logger.info("[ ✨ ] " + "Send User to Queue for deletion" + " [ ✨ ]");
        }
        catch (Exception e){
            logger.error("Error:" + e);
        }

    }
}
