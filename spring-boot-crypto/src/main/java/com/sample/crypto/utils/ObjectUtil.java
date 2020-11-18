package com.sample.crypto.utils;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * 一些通用的函数
 * 
 * @author Looly
 *
 */
public class ObjectUtil {

	private ObjectUtil() {
	}

	/**
	 * 比较两个对象是否相等。<br>
	 * 相同的条件有两个，满足其一即可：<br>
	 * <ol>
	 * <li>obj1 == null &amp;&amp; obj2 == null</li>
	 * <li>obj1.equals(obj2)</li>
	 * </ol>
	 * 1. obj1 == null &amp;&amp; obj2 == null 2. obj1.equals(obj2)
	 * 
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @return 是否相等
	 */
	public static boolean equal(Object obj1, Object obj2) {
		return (obj1 != null) ? (obj1.equals(obj2)) : (obj2 == null);
	}

	/**
	 * 比较两个对象是否不相等。<br>
	 * 
	 * @param obj1 对象1
	 * @param obj2 对象2
	 * @return 是否不等
	 * @since 3.0.7
	 */
	public static boolean notEqual(Object obj1, Object obj2) {
		return !equal(obj1, obj2);
	}

	/**
	 * 计算对象长度，如果是字符串调用其length函数，集合类调用其size函数，数组调用其length属性，其他可遍历对象遍历计算长度<br>
	 * 支持的类型包括：
	 * <ul>
	 * <li>CharSequence</li>
	 * <li>Map</li>
	 * <li>Iterator</li>
	 * <li>Enumeration</li>
	 * <li>Array</li>
	 * </ul>
	 * 
	 * @param obj 被计算长度的对象
	 * @return 长度
	 */
	public static int length(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length();
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).size();
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).size();
		}

		int count;
		if (obj instanceof Iterator) {
			Iterator<?> iter = (Iterator<?>) obj;
			count = 0;
			while (iter.hasNext()) {
				count++;
				iter.next();
			}
			return count;
		}
		if (obj instanceof Enumeration) {
			Enumeration<?> enumeration = (Enumeration<?>) obj;
			count = 0;
			while (enumeration.hasMoreElements()) {
				count++;
				enumeration.nextElement();
			}
			return count;
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj);
		}
		return -1;
	}

	/**
	 * 对象中是否包含元素<br>
	 * 支持的对象类型包括：
	 * <ul>
	 * <li>String</li>
	 * <li>Collection</li>
	 * <li>Map</li>
	 * <li>Iterator</li>
	 * <li>Enumeration</li>
	 * <li>Array</li>
	 * </ul>
	 * 
	 * @param obj     对象
	 * @param element 元素
	 * @return 是否包含
	 */
	public static boolean contains(Object obj, Object element) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			if (element == null) {
				return false;
			}
			return ((String) obj).contains(element.toString());
		}
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).contains(element);
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>) obj).values().contains(element);
		}

		if (obj instanceof Iterator) {
			Iterator<?> iter = (Iterator<?>) obj;
			while (iter.hasNext()) {
				Object o = iter.next();
				if (equal(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (obj instanceof Enumeration) {
			Enumeration<?> enumeration = (Enumeration<?>) obj;
			while (enumeration.hasMoreElements()) {
				Object o = enumeration.nextElement();
				if (equal(o, element)) {
					return true;
				}
			}
			return false;
		}
		if (obj.getClass().isArray()) {
			int len = Array.getLength(obj);
			for (int i = 0; i < len; i++) {
				Object o = Array.get(obj, i);
				if (equal(o, element)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检查对象是否为null
	 * 
	 * @param obj 对象
	 * @return 是否为null
	 */
	public static boolean isNull(Object obj) {
		return null == obj;
	}

	/**
	 * 检查对象是否不为null
	 * 
	 * @param obj 对象
	 * @return 是否为null
	 */
	public static boolean isNotNull(Object obj) {
		return null != obj;
	}

	/**
	 * 如果给定对象为{@code null}返回默认值
	 *
	 * <pre>
	 * ObjectUtil.defaultIfNull(null, null)      = null
	 * ObjectUtil.defaultIfNull(null, "")        = ""
	 * ObjectUtil.defaultIfNull(null, "zz")      = "zz"
	 * ObjectUtil.defaultIfNull("abc", *)        = "abc"
	 * ObjectUtil.defaultIfNull(Boolean.TRUE, *) = Boolean.TRUE
	 * </pre>
	 *
	 * @param <T>          对象类型
	 * @param object       被检查对象，可能为{@code null}
	 * @param defaultValue 被检查对象为{@code null}返回的默认值，可以为{@code null}
	 * @return 被检查对象为{@code null}返回默认值，否则返回原值
	 * @since 3.0.7
	 */
	public static <T> T defaultIfNull(final T object, final T defaultValue) {
		return (null != object) ? object : defaultValue;
	}

	/**
	 * 对象Clone
	 * 
	 * @see https://blog.csdn.net/xxssyyyyssxx/article/details/104365036
	 *      保证实现类重写了{@link Object#clone()}方法
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Cloneable> T clone(T t) {
		try {
			/**
			 * clone()是Object类的方法，务必保证clone的对象类重写了该方法，并且方法置为了public
			 */
			java.lang.reflect.Method method = t.getClass().getMethod("clone");
			method.setAccessible(true);
			return (T) method.invoke(t);
		} catch (Exception e) {
			throw new RuntimeException("不支持clone,请检查是否重写clone并且实现Cloneable接口", e);
		}
	}

	/**
	 * Deep clone an {@code Object} using serialization.
	 * <p>
	 * This is many times slower than writing clone methods by hand on all objects
	 * in your object graph. However, for complex object graphs, or for those that
	 * don't support deep cloning this can be a simple alternative implementation.
	 * Of course all the objects must be {@code Serializable}.
	 * </p>
	 *
	 * @param <T>    the type of the object involved
	 * @param object the {@code Serializable} object to clone
	 * @return the cloned object
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T cloneBySerialization(final T object) {
		if (object == null) {
			return null;
		}
		final byte[] objectData = serialize(object);
		return (T) deserialize(objectData);
	}

	/**
	 * Serialize the given object to a byte array.
	 *
	 * @param object the object to serialize
	 * @return an array of bytes representing the object in a portable fashion
	 */
	public static byte[] serialize(Object object) {
		if (object == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
			oos.writeObject(object);
			oos.flush();
		} catch (IOException ex) {
			throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
		}
		return baos.toByteArray();
	}

	/**
	 * Deserialize the byte array into an object.
	 *
	 * @param bytes a serialized object
	 * @return the result of deserializing the bytes
	 */
	public static Object deserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
			return ois.readObject();
		} catch (IOException ex) {
			throw new IllegalArgumentException("Failed to deserialize object", ex);
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Failed to deserialize object type", ex);
		}
	}

	/**
	 * 检查是否为有效的数字<br>
	 * 检查Double和Float是否为无限大，或者Not a Number<br>
	 * 非数字类型和Null将返回true
	 * 
	 * @param obj 被检查类型
	 * @return 检查结果，非数字类型和Null将返回true
	 */
	public static boolean isValidIfNumber(Object obj) {
		if (obj != null && obj instanceof Number) {
			if (obj instanceof Double) {
				if (((Double) obj).isInfinite() || ((Double) obj).isNaN()) {
					return false;
				}
			} else if (obj instanceof Float) {
				if (((Float) obj).isInfinite() || ((Float) obj).isNaN()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * {@code null}安全的对象比较，{@code null}对象排在末尾
	 * 
	 * @param <T> 被比较对象类型
	 * @param c1  对象1，可以为{@code null}
	 * @param c2  对象2，可以为{@code null}
	 * @return 比较结果，如果c1 &lt; c2，返回数小于0，c1==c2返回0，c1 &gt; c2 大于0
	 * @since 3.0.7
	 * @see Comparator#compare(Object, Object)
	 */
	public static <T extends Comparable<? super T>> int compare(T c1, T c2) {
		return compare(c1, c2, false);
	}

	/**
	 * {@code null}安全的对象比较
	 * 
	 * @param <T>         被比较对象类型
	 * @param c1          对象1，可以为{@code null}
	 * @param c2          对象2，可以为{@code null}
	 * @param nullGreater 当被比较对象为null时是否排在前面
	 * @return 比较结果，如果c1 &lt; c2，返回数小于0，c1==c2返回0，c1 &gt; c2 大于0
	 * @since 3.0.7
	 * @see Comparator#compare(Object, Object)
	 */
	public static <T extends Comparable<? super T>> int compare(T c1, T c2, boolean nullGreater) {
		if (c1 == c2) {
			return 0;
		} else if (c1 == null) {
			return nullGreater ? 1 : -1;
		} else if (c2 == null) {
			return nullGreater ? -1 : 1;
		}
		return c1.compareTo(c2);
	}

}
