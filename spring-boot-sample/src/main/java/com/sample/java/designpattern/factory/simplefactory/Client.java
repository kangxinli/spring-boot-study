package com.sample.java.designpattern.factory.simplefactory;

/**
 * 	
 * 工厂模式：简单工厂
 * 	1.1 描述
	简单工厂模式是由一个工厂对象根据收到的消息决定要创建哪一个类的对象实例。
	
	1.2 使用场景
	工厂类负责创建的对象比较少，客户只需要传入工厂类参数，对于如何创建对象（逻辑）不关心。简单工厂模式很容易违反高内聚低耦合的原则，因此一般只在很简单的情况下使用。
 	
 	1.3 优点
	最大的优点在于工厂类中包含了必要的逻辑，根据客户需要的逻辑动态实例化相关的类。
 *
 */
public class Client {
    public static void main(String[] args) {
        Phone phone1=PhoneFactory.createPhone("小米");
        phone1.function();
        Phone phone2=PhoneFactory.createPhone("华为");
        phone2.function();
        Phone phone3=PhoneFactory.createPhone("Iphone");
        phone3.function();
        Phone phone4=PhoneFactory.createPhone("中兴");
        phone4.function();
    }
}
