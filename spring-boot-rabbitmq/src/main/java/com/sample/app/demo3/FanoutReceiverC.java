package com.sample.app.demo3;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.sample.app.constant.Constant;

@Component
@RabbitListener(queues = Constant.QUEUE_NAME_3_C)
public class FanoutReceiverC {
    @RabbitHandler
    public void process(String message) {
        System.out.println("fanout Receiver C: " + message);
    }
}
