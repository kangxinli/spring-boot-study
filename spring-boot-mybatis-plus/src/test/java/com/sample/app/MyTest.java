package com.sample.app;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sample.app.dao.UserMapper;
import com.sample.app.entity.User;

/**
 * 逻辑删除
 * @author kang
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void deleteById() {
		int rows = userMapper.deleteById(1094592041087729666L);
		System.out.println("影响行数：" + rows);
	}
	
	@Test
	public void select() {
		List<User> list = userMapper.selectList(null);
		list.forEach(System.out::println);
	}
	
	@Test
	public void update() {
		User user = new User();
		user.setAge(26);
		user.setId(1088248166370832385L);
		int rows = userMapper.updateById(user);
		System.out.println("影响行数：" + rows);
	}

	/**
	 * 自定义sql需要手动加上删除标识
	 */
	@Test
	public void mySelect() {
		List<User> list = userMapper.selectAll(Wrappers.<User>lambdaQuery()
				.gt(User::getAge, 25).eq(User::getDeleted, 0));
		list.forEach(System.out::println);
	}
}
