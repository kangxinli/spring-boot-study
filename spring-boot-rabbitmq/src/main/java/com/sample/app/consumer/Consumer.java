package com.sample.app.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.sample.app.constant.Constant;

import cn.hutool.core.lang.Console;

@Component
public class Consumer {

    @RabbitListener(queues = Constant.QUEUE_NAME)
    public void consumeMessage(String message) {
        Console.log("consume message {}", message);
    }
}
