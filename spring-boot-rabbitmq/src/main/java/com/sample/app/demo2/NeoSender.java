package com.sample.app.demo2;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sample.app.constant.Constant;

/**
 * 工作模式
 * 一个消息产生者，多个消息的消费者。竞争抢消息
 *
 */
@Controller
public class NeoSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(int i) {
        String context = "spirng boot neo queue"+" ****** "+i;
        System.out.println("Sender1 : " + context);
        this.rabbitTemplate.convertAndSend(Constant.QUEUE_NAME_2, context);
    }
}
