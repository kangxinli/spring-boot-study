package com.sample.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.constant.Constant;
import com.sample.app.service.RabbitUtil;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Console;

@RestController
public class ProducerController {

    @Autowired
    private RabbitUtil rabbitUtil;

    /**
     * 动态创建队列
     * @return
     */
    @GetMapping("/sendMessage")
    public Object sendMessage() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                String value = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
                Console.log("send message {}", value);
                rabbitUtil.sendMessageToTopicExchange(Constant.EXCHANGE_NAME_TOPIC, Constant.ROUTING_KEY, value);
            }
        }).start();
        return "ok";
    }

}
