package com.sample.app.component;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 数据填充
 * @author kang
 *
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		Object val = getFieldValByName("updateTime", metaObject);
		if (val == null) {
			System.out.println("updateFill~~");
			strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
		}
	}

}
