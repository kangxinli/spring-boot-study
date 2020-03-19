package com.sample.app.controller;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@Autowired
	RedisLockRegistry redisLockRegistry;

	@GetMapping("test")
	public void test() throws InterruptedException {
		Lock lock = redisLockRegistry.obtain("lock");
		boolean b1 = lock.tryLock(3, TimeUnit.SECONDS);
		System.out.println("b1 is : " + b1);

		TimeUnit.SECONDS.sleep(5);

		boolean b2 = lock.tryLock(3, TimeUnit.SECONDS);
		System.out.println("b2 is : " + b2);

		lock.unlock();
		lock.unlock();
	}
}
