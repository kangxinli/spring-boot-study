package com.sample.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sample.app.config.RabbitUtil;

@RestController
public class DeviceController {
	
	@Autowired
    private RabbitUtil rabbitUtil;
	
	@GetMapping("/device")
	public void testDevice(String deviceNo, String userId) {
    	JSONObject object = new JSONObject();
    	object.put("userId", userId);
    	rabbitUtil.sendToDirectQueue("DeviceDirectExchange", "Device." + deviceNo, object.toJSONString());
    }

}
