package com.sample.java.designpattern.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 动态代理的优势在于可以很方便的对代理类的函数进行统一的处理，而不用修改每个代理类中的方法
 * 
 * 是因为所有被代理执行的方法，都是通过在InvocationHandler中的invoke方法调用的，
 * 
 * 所以我们只要在invoke方法中统一处理，就可以对所有被代理的方法进行相同的操作了
 *
 */
public class ProxyTest {

	public static void main(String[] args) {
		
		//创建一个实例对象，这个对象是被代理的对象
        Person zhangsan = new Student("张三");
        
        //创建一个与代理对象相关联的InvocationHandler
        InvocationHandler stuHandler = new StuInvocationHandler<Person>(zhangsan);
        
        //创建一个代理对象stuProxy来代理zhangsan，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
        Person stuProxy = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), 
        		new Class<?>[]{Person.class}, stuHandler);

       //代理执行上交班费的方法
        stuProxy.giveMoney();
	}
}
