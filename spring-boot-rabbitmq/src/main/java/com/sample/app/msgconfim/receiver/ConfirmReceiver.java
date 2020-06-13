package com.sample.app.msgconfim.receiver;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.sample.app.msgconfim.config.RabbitMsgConfirmConfig;

@Component
public class ConfirmReceiver {

    @RabbitListener(queues = RabbitMsgConfirmConfig.CONFIRM_QUEUE_A)
    public void process(Message message, Channel channel) throws IOException {
        System.out.println("ReceiverA："+new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
    }
}
