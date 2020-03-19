package com.sample.app.redis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTest {

	@Autowired
	private RedisUtil redisUtil;

	@GetMapping(value = "/testRedis")
	public void findInterfaceByPage() {

		redisUtil.set("String1", "str");
		System.out.println("String1:  " + redisUtil.get("String1"));
		redisUtil.set("String2", "str2", 60 * 2);
		System.out.println("String2:  " + redisUtil.get("String2"));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("int1", 1);
		map.put("int2", 2);
		map.put("int3", 3);
		redisUtil.set("map1", map, 60 * 2);
		System.out.println("map1:  " + redisUtil.get("map1"));
	}
}
