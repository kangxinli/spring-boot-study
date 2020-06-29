package com.sample.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.domain.BaseResponse;
import com.sample.app.entity.User;
import com.sample.app.repository.UserRepository;

@RequestMapping("/users")
@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 查询列表
	 * @return
	 */
	@GetMapping("/")
	public List<User> get() {
		return this.userRepository.findAll();
	}

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Optional<User> findById(@PathVariable Long id) {
		return this.userRepository.findById(id);
	}
	
	/**
	 * 新增
	 * @param user
	 * @return
	 */
	@PostMapping
	public BaseResponse<User> addUser(@RequestBody User user) {
		return BaseResponse.markSuccess(this.userRepository.save(user));
	}
	
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	@PutMapping
	public BaseResponse<User> updateUser(@RequestBody User user) {
		return BaseResponse.markSuccess(this.userRepository.save(user));
	}
	
	/**
	 * 删除
	 * @param userId
	 * @return
	 */
	@DeleteMapping(value = "/{userId}")
	public BaseResponse<User> deleteUser(@PathVariable("userId") Long userId) {
		this.userRepository.deleteById(userId);
		return BaseResponse.markSuccess(null);
	}
}
