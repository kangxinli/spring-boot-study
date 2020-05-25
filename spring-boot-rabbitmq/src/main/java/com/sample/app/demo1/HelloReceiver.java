package com.sample.app.demo1;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.sample.app.constant.Constant;

@Component
@RabbitListener(queues = Constant.QUEUE_NAME_1)
public class HelloReceiver {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }
}
