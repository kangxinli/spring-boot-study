package com.sample.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.entity.User;
import com.sample.app.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	/**
	 * localhost:8080/api/user?username=admin
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public User findOneCity(@RequestParam(value = "username", required = true) String username) {
        return userService.findByName(username);
    }

}
