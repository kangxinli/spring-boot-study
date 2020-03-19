package com.sample.java.reflect;

import java.lang.reflect.Field;

/**
 * 通过反射操作属性
 * @author kang
 *
 */

public class Hello12 {

	public static void main(String[] args) throws Exception {
		Class<?> demo = null;
        Object obj = null;
 
        demo = Class.forName("com.test.reflect.Person");
        obj = demo.newInstance();
 
        Field field = demo.getDeclaredField("sex");
        field.setAccessible(true);
        field.set(obj, "男");
        System.out.println(field.get(obj));

	}

}
