package com.sharding.demo.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sharding.demo.mapper.UserMapper;
import com.sharding.demo.model.User;
import com.sharding.demo.service.IUserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}