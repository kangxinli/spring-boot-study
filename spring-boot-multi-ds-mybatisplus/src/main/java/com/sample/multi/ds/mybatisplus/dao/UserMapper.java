package com.sample.multi.ds.mybatisplus.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sample.multi.ds.mybatisplus.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
