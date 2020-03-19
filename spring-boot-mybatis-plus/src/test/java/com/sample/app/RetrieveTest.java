package com.sample.app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sample.app.dao.UserMapper;
import com.sample.app.entity.User;

/**
 * 查询
 * @author user
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RetrieveTest {
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void selectById() {
		User user = userMapper.selectById(1087982257332887553L);
		System.out.println(user);
	}
	
	@Test
	public void selectByIds() {
		List<Long> idList = Arrays.asList(1087982257332887553L, 1088248166370832385L);
		List<User> userList = userMapper.selectBatchIds(idList);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "王天风");
		map.put("age", 25);
		// map.put("manager_id", 1087982257332887553L);
		// map key为数据库列名
		List<User> userList = userMapper.selectByMap(map);
		userList.forEach(System.out::println);
	}
	
	/**
	 * 条件构造器
	 */
	@Test
	public void selectByWrapper() {
		
		/**
		 * 名字中包含雨并且年龄小于40 
		 * 
		 * name like '%雨%' and age<40
		 */
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		// QueryWrapper<User> queryWrapper = Wrappers.query();
		queryWrapper.like("name", "雨").lt("age", 40);
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapper2() {
		/**
		 * 名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
		 * 
		 * name like '%雨%' and age between 20 and 40 and email is not null
		 */
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		
		queryWrapper.like("name", "雨").between("age", 20, 40).isNotNull("email");
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapper3() {
		/**
		 * 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
		 * 
		 * name like '王%' or age>=25 order by age desc,id asc
		 */
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		
		queryWrapper.likeRight("name", "王").or().ge("age", 25).orderByDesc("age").orderByAsc("id");
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapper4() {
		/**
		 * 创建日期为2019年2月14日并且直属上级为名字为王姓
		 * 
		 * date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
		 */
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		
		queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}", "2019-02-14")
				.inSql("manager_id", "select id from user where name like '王%'");
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapper5() {
		/**
		 * 名字为王姓并且（年龄小于40或邮箱不为空）
		 * 
		 * name like '王%' and (age<40 or email is not null)
		 */
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		
		queryWrapper.likeRight("name", "王").and(wq -> wq.lt("age", 40).or().isNotNull("email"));
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapper6() {
		/**
		 * 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
		 * 
		 * name like '王%' or (age<40 and age>20 and email is not null)
		 */
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		
		queryWrapper.likeRight("name", "王")
				.or(wq -> wq.lt("age", 40).gt("age", 20).isNotNull("email"));
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapper7() {
		/**
		 * （年龄小于40或邮箱不为空）并且名字为王姓
		 * 
		 * (age<40 or email is not null) and name like '王%'
		 */
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		
		queryWrapper.nested(wq -> wq.lt("age", 40).or().isNotNull("email"))
				.likeRight("name", "王");
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapper8() {
		/**
		 * 年龄为30、31、34、35
		 * 
		 * age in (30、31、34、35)
		 */
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		
		queryWrapper.in("age", Arrays.asList(30, 31, 34, 35));
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapper9() {
		/**
		 * 只返回满足条件的其中一条语句即可
		 * 
		 * limit 1
		 */
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		
		queryWrapper.in("age", Arrays.asList(30, 31, 34, 35)).last("limit 1");
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}

	/**
	 * 条件构造器
	 */
	@Test
	public void selectByWrapperSupper() {
		
		/**
		 * 名字中包含雨并且年龄小于40 
		 * 
		 * name like '%雨%' and age<40
		 */
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		// QueryWrapper<User> queryWrapper = Wrappers.query();
		queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	/**
	 * 条件构造器
	 */
	@Test
	public void selectByWrapperSupper2() {
		
		/**
		 * 名字中包含雨并且年龄小于40 
		 * 
		 * name like '%雨%' and age<40
		 */
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		// QueryWrapper<User> queryWrapper = Wrappers.query();
		queryWrapper.like("name", "雨").lt("age", 40)
				.select(User.class, info -> !info.getColumn().equals("manager_id") 
						&& !info.getColumn().equals("create_time"));
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void testCondition() {
		String name = "王";
		String email = "";
		condition(name, email);
	}
	
	/**
	 * 条件查询
	 * @param name
	 * @param email
	 */
	@SuppressWarnings("deprecation")
	private void condition(String name, String email) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
//		if (StringUtils.isNotEmpty(name)) {
//			queryWrapper.like("name", name);
//		}
//		if (StringUtils.isNotEmpty(email)) {
//			queryWrapper.like("email", email);
//		}
		queryWrapper.like(StringUtils.isNotEmpty(name), "name", name)
				.like(StringUtils.isNotEmpty(email), "email", email);
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapperEntity() {
		User whereUser = new User();
		whereUser.setName("刘红雨");
		whereUser.setAge(32);
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>(whereUser);
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapperAllEq() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		Map<String, Object> params = new HashMap<>();
		params.put("name", "王天风");
		params.put("age", 25);
		queryWrapper.allEq(params);
		
		List<User> userList = userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapperMaps() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);
		
		List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapperMaps2() {
		/**
		 * 11、按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。并且只取年龄总和小于500的组
		 * 
		 * select avg(age) avg_age,min(age) min_age,max(age) max_age
			from user
			group by manager_id
			having sum(age) <500
		 * 
		 */
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
				.groupBy("manager_id").having("sum(age)<{0}", 500);
		
		List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapperObjs() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);
		
		// 适用于只返回一列
		List<Object> userList = userMapper.selectObjs(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectByWrapperCount() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.like("name", "雨").lt("age", 40);
		
		Integer count = userMapper.selectCount(queryWrapper);
		System.out.println("总记录数：" + count);
	}
	
	@Test
	public void selectByWrapperOne() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.like("name", "刘红雨").lt("age", 40);
		
		// 查询结果不能大于1条
		User user = userMapper.selectOne(queryWrapper);
		System.out.println(user);
	}
	
	@Test
	public void selectLambda() {
		// LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
		// LambdaQueryWrapper<User> lambda = new LambdaQueryWrapper<User>();
		LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
		// where name like '%雨%'
		lambdaQuery.like(User::getName, "雨").lt(User::getAge, 40);
		
		List<User> userList = userMapper.selectList(lambdaQuery);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectLambda2() {
		/**
		 * （年龄小于40或邮箱不为空）并且名字为王姓
		 * 
		 * (age<40 or email is not null) and name like '王%'
		 */
		LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
		lambdaQuery.likeRight(User::getName, "王")
				.and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
		
		List<User> userList = userMapper.selectList(lambdaQuery);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectLambda3() {
		List<User> userList = new LambdaQueryChainWrapper<User>(userMapper)
				.like(User::getName, "雨").ge(User::getAge, 20).list();
		userList.forEach(System.out::println);
	}
	
	/**
	 * 自定义
	 */
	@Test
	public void selectMy() {
		/**
		 * （年龄小于40或邮箱不为空）并且名字为王姓
		 * 
		 * (age<40 or email is not null) and name like '王%'
		 */
		LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
		lambdaQuery.likeRight(User::getName, "王")
				.and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
		
		List<User> userList = userMapper.selectAll(lambdaQuery);
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectPage() {
		
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.ge("age", 26);
		
		Page<User> page = new Page<User>(1, 2);
		
		
		IPage<User> iPage = userMapper.selectPage(page, queryWrapper);
		System.out.println("总页数：" + iPage.getPages()); 
		System.out.println("总记录数：" + iPage.getTotal()); 
		List<User> userList = iPage.getRecords();
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectMyPage() {
		
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.ge("age", 26);
		
		Page<User> page = new Page<User>(1, 2);
		
		
		IPage<User> iPage = userMapper.selectUserPage(page, queryWrapper);
		System.out.println("总页数：" + iPage.getPages());
		System.out.println("总记录数：" + iPage.getTotal());
		List<User> userList = iPage.getRecords();
		userList.forEach(System.out::println);
	}
}
