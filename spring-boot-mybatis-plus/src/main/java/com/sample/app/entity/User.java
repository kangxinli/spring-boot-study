package com.sample.app.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("user")
public class User {
	// 主键
	@TableId
	private Long id;
	// 姓名
	@TableField("name")
	private String name;
	// 年龄
	private Integer age;
	// 邮箱
	private String email;
	// 直属上级
	private Long managerId;
	// 创建时间
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	// 修改时间
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;
	// 版本
	private Integer version;
	// 逻辑删除标识(0:未删除, 1:已删除)
	@TableLogic
	@TableField(select = false)	// 查询时不显示
	private Integer deleted;
	// 非数据库字段
	@TableField(exist = false)
	private String remark;

}
