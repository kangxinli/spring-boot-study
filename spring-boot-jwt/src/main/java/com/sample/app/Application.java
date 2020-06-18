package com.sample.app;

import java.util.ArrayList;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sample.app.entity.Role;
import com.sample.app.entity.User;
import com.sample.app.repository.UserRepository;
import com.sample.app.service.UserService;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.run(args);
	}

	@Autowired
	UserService userService;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	ApplicationRunner init(UserRepository repository) {
		return args -> {
			User user1 = new User(1, "admin", "admin", "admin@email.com",
					new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
			User user2 = new User(2, "client", "client", "client@email.com",
					new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
			userService.signup(user1);
			userService.signup(user2);
		};
	}
}