package com.sample.app.msgconfim.sender;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sample.app.msgconfim.config.RabbitMsgConfirmConfig;

@Component
public class ConfirmSender {
    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;


    public void send(){
        String content="hello this is a confirm message ";
        System.out.println("Confirm Sender："+content);
        rabbitMessagingTemplate.convertAndSend(RabbitMsgConfirmConfig.CONFIRM_EXCHANGE,"topic.message",content);
    }
}
