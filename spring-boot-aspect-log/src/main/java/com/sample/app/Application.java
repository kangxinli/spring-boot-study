package com.sample.app;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sample.app.entity.User;
import com.sample.app.repository.UserRepository;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.run(args);
	}
	
	@Bean
	ApplicationRunner init(UserRepository repository) {
		return args -> {
			User user1 = new User(1L, "account1", "张三", 20, new BigDecimal(100.00));
			User user2 = new User(2L, "account2", "李四", 28, new BigDecimal(180.00));
			User user3 = new User(3L, "account3", "王五", 32, new BigDecimal(280.00));
			Stream.of(user1, user2, user3).forEach(repository::save);
		};
	}
}