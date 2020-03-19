package com.sample.app;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.app.dao.UserMapper;
import com.sample.app.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void select() {
		List<User> list = userMapper.selectList(null);
		list.forEach(System.out::println);
	}

}
