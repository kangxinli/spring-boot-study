package com.sample.java.reflect;

import java.lang.reflect.Constructor;

public class Hello7 {

	public static void main(String[] args) {
		Class<?> demo=null;
        try{
            demo=Class.forName("com.test.reflect.Person");
        }catch (Exception e) {
            e.printStackTrace();
        }
        Constructor<?>cons[]=demo.getConstructors();
        for (int i = 0; i < cons.length; i++) {
            System.out.println("构造方法：  "+cons[i]);
        }

	}

}
