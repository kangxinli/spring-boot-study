package com.sample.crypto.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 实体工具类
 * 
 * @author L.cm email: 596392912@qq.com site:http://www.dreamlu.net date
 *         2015年4月26日下午5:10:42
 */
public class BeanUtil {
	private BeanUtil() {
	}

	/**
	 * 实例化对象
	 * 
	 * @param clazz 类
	 * @param <T>   type parameter
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<?> clazz) {
		try {
			return (T) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取JavaBean风格的属性值
	 */
	public static Object get(Object o, String propertyName) {
		try {
			Class<?> clazz = o.getClass();
			Method getMethod = clazz.getMethod(StrUtil.genGetter(propertyName));
			return getMethod.invoke(o);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 递归获取某个类的所有的属性，包括其父类的 getDeclaredFields 获取某个类的所有的字段，包括私有的，但是不包括父类的 getFields
	 * 获得某个类的所有的公共（public）的字段，包括父类中的字段
	 * 
	 * @param clazz     class
	 * @param list      传入一个list，接收field
	 * @param predicate 过滤器
	 */
	public static void parseAllFields(Class<?> clazz, List<Field> list, Predicate<Field> predicate) {
		if (clazz == Object.class) {
			return;
		}

		Field[] fields = clazz.getDeclaredFields();
		// 无则不过滤，有则过滤
		if (null == predicate) {
			list.addAll(Arrays.stream(fields).collect(Collectors.toList()));
		} else {
			list.addAll(Arrays.stream(fields).filter(predicate).collect(Collectors.toList()));
		}
		parseAllFields(clazz.getSuperclass(), list, predicate);
	}

	public static void parseAllFields(Class<?> clazz, List<Field> list) {
		parseAllFields(clazz, list, null);
	}

	public static boolean canSetValueDirectly(Class<?> clazz) {
		return clazz.isPrimitive() // 基本类型
				|| clazz == String.class // String
				|| clazz == Character.class // Character
				|| clazz == Boolean.class // Boolean
				|| Number.class.isAssignableFrom(clazz) // Double Float Long Integer Short Byte BigInteger BigDecimal
				|| java.util.Date.class.isAssignableFrom(clazz) // java.sql.Date,Time,TimeStamp
		;
	}

	public static <T> void setValue(T instance, Field field, Object v) {
		field.setAccessible(true);
		try {
			v = compatibleValue(v, field.getType());
			field.set(instance, v);
		} catch (IllegalAccessException e) {
		}
	}

	/**
	 * 将一个值转换为兼容类型的值
	 * 
	 * @param value   src
	 * @param toClazz 转换的类型
	 */
	public static Object compatibleValue(Object value, Class<?> toClazz) {
		if (toClazz == Byte.class || toClazz == byte.class) {
			if (value instanceof Number) {
				return ((Number) value).byteValue();
			} else if (value instanceof String) {
				return Byte.valueOf((String) value);
			}
		}
		if (toClazz == Short.class || toClazz == short.class) {
			if (value instanceof Number) {
				return ((Number) value).shortValue();
			} else if (value instanceof String) {
				return Short.valueOf((String) value);
			}
		}
		if (toClazz == Integer.class || toClazz == int.class) {
			if (value instanceof Number) {
				return ((Number) value).intValue();
			} else if (value instanceof String) {
				return Integer.valueOf((String) value);
			}
		}
		if (toClazz == Long.class || toClazz == long.class) {
			if (value instanceof Number) {
				return ((Number) value).longValue();
			} else if (value instanceof String) {
				return Long.valueOf((String) value);
			}
		}
		if (toClazz == Double.class || toClazz == double.class) {
			if (value instanceof Number) {
				return ((Number) value).doubleValue();
			} else if (value instanceof String) {
				return Double.valueOf((String) value);
			}
		}
		if (toClazz == Float.class || toClazz == float.class) {
			if (value instanceof Number) {
				return ((Number) value).floatValue();
			} else if (value instanceof String) {
				return Float.valueOf((String) value);
			}
		}
		if (toClazz == BigDecimal.class) {
			return new BigDecimal(value.toString());
		}
		if (toClazz == BigInteger.class) {
			return new BigInteger(value.toString());
		}
		if (toClazz == Boolean.class || toClazz == boolean.class) {
			return Boolean.valueOf(value.toString());
		}
		if (toClazz == Character.class || toClazz == char.class) {
			return value;
		}

		if (toClazz == String.class) {
			return value.toString();
		}

		return value;
	}
}
