package com.sample.app.demo2;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.sample.app.constant.Constant;

@Component
@RabbitListener(queues = Constant.QUEUE_NAME_2)
public class NeoReceiver2 {
    @RabbitHandler
    public void process(String neo) {
        System.out.println("Receiver 2: " + neo);
    }

}
