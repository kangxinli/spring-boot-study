package com.sharding.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sharding.demo.model.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
