package com.sample.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ConfigurationProperties(prefix = "rocketmq.consumer")
@Configuration
@ToString
public class ConsumerConfig {
    private String groupName;
    
    private String namesrvAddr;
}
