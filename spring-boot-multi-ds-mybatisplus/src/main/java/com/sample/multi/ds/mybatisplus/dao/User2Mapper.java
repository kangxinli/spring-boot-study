package com.sample.multi.ds.mybatisplus.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sample.multi.ds.mybatisplus.entity.User2;

@DS("db2") 
@Mapper
public interface User2Mapper extends BaseMapper<User2> {

}
