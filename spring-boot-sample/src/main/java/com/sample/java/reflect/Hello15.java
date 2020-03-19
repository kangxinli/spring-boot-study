package com.sample.java.reflect;

class test{
    
}

/**
 * 看看如何获得类加载器：
 * 其实在java中有三种类类加载器。
 * 1）Bootstrap ClassLoader 此加载器采用c++编写，一般开发中很少见。
 * 2）Extension ClassLoader 用来进行扩展类的加载，一般对应的是jre\lib\ext目录中的类
 * 3）AppClassLoader 加载classpath指定的类，是最常用的加载器。同时也是java中默认的加载器。
 * @author kang
 *
 */

public class Hello15 {

	public static void main(String[] args) {
		test t=new test();
        System.out.println("类加载器  "+t.getClass().getClassLoader().getClass().getName());

	}

}
