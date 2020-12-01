package com.sample.multi.ds.mybatisplus.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sample.multi.ds.mybatisplus.dao.UserMapper;
import com.sample.multi.ds.mybatisplus.entity.User;
import com.sample.multi.ds.mybatisplus.service.IUserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}