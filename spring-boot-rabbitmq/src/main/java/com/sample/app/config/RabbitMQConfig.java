package com.sample.app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sample.app.constant.Constant;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbit.host}")
    private String host;
    
    @Value("${spring.rabbit.port}")
    private int port;
    
    @Value("${spring.rabbit.username}")
    private String username;
    
    @Value("${spring.rabbit.password}")
    private String password;

    // 创建队列
    @Bean
    public Queue queue() {
        return new Queue(Constant.QUEUE_NAME);
    }
    
    // 简单模式
    @Bean
    public Queue queue1() {
        return new Queue(Constant.QUEUE_NAME_1);
    }
    
    // 工作模式
    @Bean
    public Queue Queue2() {
        return new Queue(Constant.QUEUE_NAME_2);
    }
    
    // === 发布订阅模式 ===
    @Bean
    public Queue AMessage() {
        return new Queue(Constant.QUEUE_NAME_3_A);
    }

    @Bean
    public Queue BMessage() {
        return new Queue(Constant.QUEUE_NAME_3_B);
    }

    @Bean
    public Queue CMessage() {
        return new Queue(Constant.QUEUE_NAME_3_C);
    }
    // === 发布订阅模式 end ===
    
    @Bean
    public Queue queueMessage() {
        return new Queue(Constant.QUEUE_NAME_5_A);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(Constant.QUEUE_NAME_5_B);
    }
    
    // 创建一个 Fanout 类型的交换器
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(Constant.EXCHANGE_NAME_FANOUT);
    }
    
    // 把这些队列绑定到交换机上去
    @Bean
    Binding bindingExchangeA(Queue AMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }

    // 创建一个 topic 类型的交换器
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Constant.EXCHANGE_NAME_TOPIC);
    }

    // 使用路由键（routingKey）把队列（Queue）绑定到交换器（Exchange）
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(Constant.ROUTING_KEY);
    }
    
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }
    
    // topic模式，前缀匹配到topic.即可接受
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

}
