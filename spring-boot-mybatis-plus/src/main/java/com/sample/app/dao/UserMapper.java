package com.sample.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sample.app.entity.User;

public interface UserMapper extends BaseMapper<User> {
	
	// 自定义sql
	// @Select("select * from user ${ew.customSqlSegment}")
	List<User> selectAll(@Param(Constants.WRAPPER) Wrapper<User> wrapper);

	IPage<User> selectUserPage(Page<User> page, @Param(Constants.WRAPPER) Wrapper<User> wrapper);
}
