package com.sample.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.sample.app.dao.UserMapper;
import com.sample.app.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateTest {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void updateById() {
		User user = new User();
		user.setId(1088248166370832385L);;
		user.setAge(26);
		user.setEmail("wtf2@baomidou.com");
		int rows = userMapper.updateById(user);
		System.out.println("影响记录数：" + rows);
	}
	
	@Test
	public void updateByWrapper() {
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("name", "李艺伟").eq("age", 28);
		
		User user = new User();
		user.setEmail("lyw2020@baomidou.com");
		user.setAge(29);
		int rows = userMapper.update(user, updateWrapper);
		System.out.println("影响记录数：" + rows);
	}
	
	@Test
	public void updateByWrapper1() {
		User whereUser = new User();
		whereUser.setName("李艺伟");
		
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>(whereUser);
		updateWrapper.eq("name", "李艺伟").eq("age", 28);
		
		User user = new User();
		user.setEmail("lyw2020@baomidou.com");
		user.setAge(29);
		int rows = userMapper.update(user, updateWrapper);
		System.out.println("影响记录数：" + rows);
	}
	
	@Test
	public void updateByWrapper2() {
		User whereUser = new User();
		whereUser.setName("李艺伟");
		
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>(whereUser);
		updateWrapper.eq("name", "李艺伟").eq("age", 28);
		
		User user = new User();
		user.setEmail("lyw2020@baomidou.com");
		user.setAge(29);
		int rows = userMapper.update(user, updateWrapper);
		System.out.println("影响记录数：" + rows);
	}
	
	@Test
	public void updateByWrapper3() {
		
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("name", "李艺伟").eq("age", 29).set("age", 30);
		
		int rows = userMapper.update(null, updateWrapper);
		System.out.println("影响记录数：" + rows);
	}
	
	@Test
	public void updateByWrapperLambda() {
		LambdaUpdateWrapper<User> lambdaUpdate = Wrappers.lambdaUpdate();
		lambdaUpdate.eq(User::getName, "李艺伟").eq(User::getAge, 30).set(User::getAge, 31);
		
		int rows = userMapper.update(null, lambdaUpdate);
		System.out.println("影响记录数：" + rows);
	}
	
	@Test
	public void updateByWrapperLambdaChain() {
		boolean update = new LambdaUpdateChainWrapper<User>(userMapper)
				.eq(User::getName, "李艺伟").eq(User::getAge, 31).set(User::getAge, 32).update();
		System.out.println(update);
	}

}
