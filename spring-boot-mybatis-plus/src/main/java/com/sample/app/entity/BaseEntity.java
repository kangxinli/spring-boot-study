package com.sample.app.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import lombok.Data;

@Data
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonSerialize(using = DateSerializer.class)
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonSerialize(using = DateSerializer.class)
    private Date updateTime;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableField("del_flag")
    private String delFlag;

}
