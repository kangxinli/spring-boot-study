package com.sample.java.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 如果想要完成动态代理，首先需要定义一个InvocationHandler接口的子类，已完成代理的具体操作
 *
 */

// 定义项目接口
interface Subject {
	public String say(String name, int age);
}

// 定义真实项目
class RealSubject implements Subject {
	@Override
	public String say(String name, int age) {
		return name + "  " + age;
	}
}

class MyInvocationHandler implements InvocationHandler {
	private Object obj = null;

	public Object bind(Object obj) {
		this.obj = obj;
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object temp = method.invoke(this.obj, args);
		return temp;
	}
}

public class Hello16 {

	public static void main(String[] args) {
		MyInvocationHandler demo = new MyInvocationHandler();
		Subject sub = (Subject) demo.bind(new RealSubject());
		String info = sub.say("Rollen", 20);
		System.out.println(info);
	}
}
