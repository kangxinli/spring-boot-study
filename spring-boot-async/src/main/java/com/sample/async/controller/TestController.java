package com.sample.async.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.async.service.TaskService;

@RestController
public class TestController {
	
	@Autowired
	TaskService taskService;
	
	@RequestMapping("/testAsync")
	public void testAsync() {
		taskService.testAsync();
	}

}
