package com.sample.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sample.app.domain.BaseResponse;
import com.sample.app.entity.User;

public class BaseController<M extends BaseMapper<T>, T> {
	
	@Autowired
    protected M baseMapper;

	/**
	 * 查询列表
	 * @return
	 */
	@GetMapping("/")
	public List<T> get() {
		QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
		return baseMapper.selectList(queryWrapper);
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public BaseResponse<T> findById(@PathVariable Long id) {
		return BaseResponse.markSuccess(baseMapper.selectById(id));
	}
	
	/**
	 * 新增
	 * @param user
	 * @return
	 */
	@PostMapping
	public BaseResponse<Integer> addUser(@RequestBody T entity) {
		return BaseResponse.markSuccess(baseMapper.insert(entity));
	}
	
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	@PutMapping
	public BaseResponse<Integer> updateUser(@RequestBody T entity) {
		return BaseResponse.markSuccess(baseMapper.insert(entity));
	}
	
	/**
	 * 删除
	 * @param userId
	 * @return
	 */
	@DeleteMapping(value = "/{id}")
	public BaseResponse<User> deleteUser(@PathVariable("id") Long id) {
		baseMapper.deleteById(id);
		return BaseResponse.markSuccess(null);
	}

}
