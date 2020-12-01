package com.sample.multi.ds.mybatisplus.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sample.multi.ds.mybatisplus.dao.User2Mapper;
import com.sample.multi.ds.mybatisplus.entity.User2;
import com.sample.multi.ds.mybatisplus.service.IUser2Service;

@Service
public class User2ServiceImpl extends ServiceImpl<User2Mapper, User2> implements IUser2Service {

}