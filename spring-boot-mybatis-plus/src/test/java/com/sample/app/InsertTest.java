package com.sample.app;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.app.dao.UserMapper;
import com.sample.app.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertTest {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void insert() {
		User user = new User();
		user.setName("刘明强");
		user.setAge(20);
		user.setManagerId(1088248166370832385L);
		user.setCreateTime(LocalDateTime.now());
		int rows = userMapper.insert(user);
		System.out.println("影响记录数：" + rows);
	}

}
