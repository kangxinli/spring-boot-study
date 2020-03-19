package com.sample.java.reflect;

import java.lang.reflect.Method;

/**
 * 通过反射调用其他类中的方法
 * @author kang
 *
 */

public class Hello10 {

	public static void main(String[] args) {
		Class<?> demo = null;
        try {
            demo = Class.forName("com.test.reflect.Person");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            //调用Person类中的sayChina方法
            Method method=demo.getMethod("sayChina");
            method.invoke(demo.newInstance());
            //调用Person的sayHello方法
            method=demo.getMethod("sayHello", String.class,int.class);
            method.invoke(demo.newInstance(),"Rollen",20);
             
        }catch (Exception e) {
            e.printStackTrace();
        }

	}

}
