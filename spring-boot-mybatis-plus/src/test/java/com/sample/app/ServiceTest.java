package com.sample.app;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sample.app.entity.User;
import com.sample.app.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void getOne() {
		User one = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 25), false);
		System.out.println(one);
	}
	
	@Test
	public void Batch() {
		User user1 = new User();
		user1.setName("徐丽");
		user1.setAge(28);
		
		User user2 = new User();
		user2.setId(1094592041087729666L);
		user2.setName("刘红雨");
		user2.setAge(28);
		List<User> userList = Arrays.asList(user1, user2);
		boolean saveBatch = userService.saveOrUpdateBatch(userList);
		System.out.println(saveBatch);
	}
	
	@Test
	public void chain() {
		List<User> userList = userService.lambdaQuery().gt(User::getAge, 25).like(User::getName, "雨").list();
		userList.forEach(System.out::println);
	}
	
	@Test
	public void chain1() {
		boolean update = userService.lambdaUpdate().eq(User::getAge, 25).set(User::getName, "雨").update();
		System.out.println(update);
	}
	
	@Test
	public void chain2() {
		boolean remove = userService.lambdaUpdate().eq(User::getAge, 24).remove();
		System.out.println(remove);
	}
}
