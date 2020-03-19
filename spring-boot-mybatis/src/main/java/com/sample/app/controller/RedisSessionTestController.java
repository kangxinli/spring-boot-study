package com.sample.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisSessionTestController {
	
	@GetMapping("/set")
    public void test(HttpServletRequest request){
        request.getSession().setAttribute("message",request.getQueryString());
    }
    @GetMapping("/get")
    public Map<String,Object> two(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("sessionId",request.getSession().getId());
        map.put("message",request.getSession().getAttribute("message"));
        return map;
    }

}
