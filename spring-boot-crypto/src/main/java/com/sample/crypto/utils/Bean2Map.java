package com.sample.crypto.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bean2Map {
	/**
	 * 转换bean为map
	 *
	 * @param source 要转换的bean
	 * @param <T>    bean类型
	 * @return 转换结果
	 */
	public static <T> Map<String, Object> convert(T source, String... ignoreFields) {
		Map<String, Object> result = new HashMap<>(10);

		try {
			Class<?> sourceClass = source.getClass();
			// 拿到所有的字段,不包括继承的字段
			// Field[] sourceFiled = sourceClass.getDeclaredFields();
			List<Field> fields = new ArrayList<>();
			BeanUtil.parseAllFields(sourceClass, fields, Bean2Map::accepted);
			for (Field field : fields) {
				// 设置可访问,不然拿不到private
				field.setAccessible(true);

				if (ArrayUtil.isNotEmpty(ignoreFields) && ArrayUtil.contains(ignoreFields, field.getName())) {
					continue;
				}

				// 配置了注解的话则使用注解名称,作为header字段
				FieldName fieldName = field.getAnnotation(FieldName.class);
				if (fieldName == null) {
					result.put(field.getName(), field.get(source));
				} else {
					if (fieldName.ignore())
						continue;
					result.put(fieldName.value(), field.get(source));
				}
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	private static boolean accepted(Field field) {
		return true;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
	public @interface FieldName {
		/**
		 * 字段名
		 */
		String value();

		/**
		 * 是否忽略
		 */
		boolean ignore() default false;
	}
}
