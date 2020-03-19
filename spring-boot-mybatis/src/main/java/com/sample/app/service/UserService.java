package com.sample.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.app.dao.UserMapper;
import com.sample.app.entity.User;

import tk.mybatis.mapper.entity.Example;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;

	public User findByName(String username) {
		User demo = new User();
		demo.setUserName(username);
		
		// select 接口
		/**
		 * select : 根据实体中的属性值进行查询，查询条件使用等号 
		 * selectOne : 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号 
		 * selectAll : 查询全部结果，select(null)方法能达到同样的效果 
		 * selectByPrimaryKey : 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号 
		 * selectCount : 根据实体中的属性查询总数，查询条件使用等号
		 * 
		 */
		List<User> list = userMapper.select(demo); 
		System.out.println("select : ");
		printList(list);
		
		User one = userMapper.selectOne(demo); 
		if (one != null) {
			System.out.println("selectOne : " + one.toString());
		}
		
		int count = userMapper.selectCount(demo);
		System.out.println("count : " + count);
		
		// insert 
		/**
		 * insert : 保存一个实体，null的属性也会保存，不会使用数据库默认值 
		 * insertSelective : 保存实体，null的属性不会保存，会使用数据库默认值
		 */
		
		// userMapper.insert(demo);
		
		// update接口
		/**
		 * updateByPrimaryKeySelective : 根据主键更新属性不为null的值 
		 * updateByPrimaryKey : 根据主键更新实体全部字段，null值会被更新
		 */
		
		// delete接口
		/**
		 * delete : 根据实体属性作为条件进行删除，查询条件使用等号 
		 * deleteByPrimaryKey : 根据主键字段进行删除，方法参数必须包含完整的主键属性
		 */
		
		//（2）Example方法
	    Example example = new Example(User.class);
	    example.createCriteria().andLike("userName", username + "%");
	    //自定义查询
	    List<User> list3 = userMapper.selectByExample(example);
	    System.out.println("selectByExample : ");
	    printList(list3);
		
		return null;
	}
	
	private void printList(List<User> list) {
		
		for (User demo : list) {
			System.out.println(demo.toString());
		}
		
	}
}
