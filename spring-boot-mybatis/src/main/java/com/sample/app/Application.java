package com.sample.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import tk.mybatis.spring.annotation.MapperScan;

@EnableRedisHttpSession		//开启redis集中式session管理，所有的session都存放到了redis中
@SpringBootApplication
@MapperScan("com.sample.app.dao")
public class Application {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.run(args);
	}
}