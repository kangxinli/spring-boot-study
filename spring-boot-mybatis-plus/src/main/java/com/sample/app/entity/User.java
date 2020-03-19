package com.sample.app.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("user")
public class User {
	
	@TableId
	private Long id;
	
	@TableField("name")
	private String name;
	
	private Integer age;
	
	private String email;
	
	private Long managerId;
	
	private LocalDateTime createTime;
	
	// 非数据库字段
	@TableField(exist = false)
	private String remark;

}
