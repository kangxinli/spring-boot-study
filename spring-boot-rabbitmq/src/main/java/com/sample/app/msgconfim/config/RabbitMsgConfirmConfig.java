package com.sample.app.msgconfim.config;

import javax.annotation.Resource;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMsgConfirmConfig {
    public static final String CONFIRM_QUEUE_A = "confirm_queue_A";
    public static final String CONFIRM_QUEUE_B = "confirm_queue_B";
    public static final String CONFIRM_EXCHANGE = "confirm_topic_exchange";
    private static final String CONFIRM_QUEUE_A_RoutingKey="topic.message";
    private static final String CONFIRM_QUEUE_B_RoutingKey="topic.#";

    @Resource
    RabbitTemplate rabbitTemplate;
    @Bean
    public Queue confirmQueryA() {
        return new Queue(CONFIRM_QUEUE_A);
    }

    @Bean
    public Queue confirmQueryB() {
        return new Queue(CONFIRM_QUEUE_B);
    }


    @Bean
    TopicExchange confirmTopicExchange() {
        return new TopicExchange(CONFIRM_EXCHANGE);
    }

    @Bean
    Binding bindingConfirmExchangeA(Queue confirmQueryA, TopicExchange confirmTopicExchange) {
        return BindingBuilder.bind(confirmQueryA).to(confirmTopicExchange).with(CONFIRM_QUEUE_A_RoutingKey);
    }

    @Bean
    Binding bindingConfirmExchangeB(Queue confirmQueryB, TopicExchange confirmTopicExchange) {
        return BindingBuilder.bind(confirmQueryB).to(confirmTopicExchange).with(CONFIRM_QUEUE_B_RoutingKey);
    }


    @Bean
    public RabbitMessagingTemplate rabbitMessagingTemplate() {
        //消息发送到交换器Exchange后触发回调
        rabbitTemplate.setConfirmCallback(new ConfirmCallBackHandler());
        //如果消息从交换器发送到对应队列失败时触发
        rabbitTemplate.setReturnCallback(new ReturnCallBackHandler());
        RabbitMessagingTemplate rabbitMessagingTemplate = new RabbitMessagingTemplate();
        rabbitMessagingTemplate.setRabbitTemplate(rabbitTemplate);
        return rabbitMessagingTemplate;
    }

}
