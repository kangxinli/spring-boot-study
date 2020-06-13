package com.sample.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * rabbitmq 六种模式
 * 1： 简单模式 simple
 * 2： 工作模式 work
 * 3： 发布订阅模式 publish/subscribe
 * 4： 路由模式 routing
 * 5： 主题模式(路由模式的一种) topic
 * 6： RPC
 */
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.run(args);
	}
}