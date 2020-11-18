package com.sample.crypto.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 将查询结果 map 封装成对应的javaBean，支持级联 ，但是属性不能重复 对应的javaBean的属性名必须以小驼峰形式命名，否则无法填充数据
 */
public class Map2Bean {

	private Map2Bean() {
	}

	/**
	 * 将 map 数据封装成javaBean
	 * 
	 * @param map   Map类型数据
	 * @param clazz 需要转换的JavaBean
	 * @param <T>   泛型
	 * @return JavaBean
	 */
	public static <T> T convert(Map<String, Object> map, Class<T> clazz) {
		try {
			final T instance = clazz.newInstance();
			// Field[] fields = clazz.getDeclaredFields();
			List<Field> fields = new ArrayList<>();
			BeanUtil.parseAllFields(clazz, fields, Map2Bean::accepted);

			for (Field field : fields) {
				String fieldName = field.getName();
				Class<?> type = field.getType();
				if (!BeanUtil.canSetValueDirectly(type)) {
					BeanUtil.setValue(instance, field, convert(map, type));
				} else {
					Object value = map.get(fieldName);
					if (null == value) {
						// 尝试转换为下划线的方式再获取一次
						value = map.get(StrUtil.toUnderlineCase(fieldName));
						// null没得必要设置
						if (null == value) {
							continue;
						}

					}
					BeanUtil.setValue(instance, field, value);
				}
			}

			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 需要跳过static和final的
	 */
	private static boolean accepted(Field field) {
		Class<?> type = field.getType();
		// 因为这些数据结构无法赋值，所以排除掉
		if (Map.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type) || type.isArray()) {
			return false;
		}
		int mod = field.getModifiers();
		return !(Modifier.isStatic(mod) || Modifier.isFinal(mod));
	}
}
