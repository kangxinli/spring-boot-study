package com.sample.app.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class ProducerConfigure {

    @Autowired
    private ProducerConfig producerConfigure;

    /**
     * 创建普通消息发送者实例
     * 1. RocketMQ版本必须是4.3，低于4.3会报出“RocketMQ No route info of this topic”；
     * 2. 生产者配置类里的注解@ConditionalOnProperty(prefix = "rocketmq.producer", value = "default", havingValue = "true")，
     * 	     这个havingValue 的值是true，和配置文件中的rocketmq.producer.default得值不一致，会导致启动报错。
     * 
     * @return
     * @throws MQClientException
     */
    @Bean
    @ConditionalOnProperty(prefix = "rocketmq.producer", value = "default", havingValue = "false")
    public DefaultMQProducer defaultProducer() throws MQClientException {
        log.info(producerConfigure.toString());
        log.info("defaultProducer 正在创建---------------------------------------");
        DefaultMQProducer producer = new DefaultMQProducer(producerConfigure.getGroupName());
        producer.setNamesrvAddr(producerConfigure.getNamesrvAddr());
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(10);
        producer.start();
        log.info("rocketmq producer server开启成功---------------------------------.");
        return producer;
    }
    
    /**
     * 创建事务消息发送者实例
     * 
     * @return
     * @throws MQClientException
     */
    @Bean
    @ConditionalOnProperty(prefix = "rocketmq.producer", value = "transaction", havingValue = "true")
    public TransactionMQProducer transactionMQProducer() throws MQClientException {
        log.info(producerConfigure.toString());
        log.info("TransactionMQProducer 正在创建---------------------------------------");
        TransactionMQProducer producer = new TransactionMQProducer(producerConfigure.getGroupName());

        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("client-transaction-msg-check-thread");
                        return thread;
                    }
                });
        producer.setNamesrvAddr(producerConfigure.getNamesrvAddr());
        producer.setExecutorService(executorService);
        producer.start();
        log.info("TransactionMQProducer server开启成功---------------------------------.");
        return producer;
    }
}
