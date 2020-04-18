package com.sample.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.entity.User;
import com.sample.app.repository.UserRepository;

@RequestMapping("/users")
@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/{id}")
	public Optional<User> findById(@PathVariable Long id) {
		return this.userRepository.findById(id);
	}
}
