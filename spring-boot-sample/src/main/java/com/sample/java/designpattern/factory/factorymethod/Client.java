package com.sample.java.designpattern.factory.factorymethod;

/**
 * 
 * 工厂模式：工厂方法
	2.1 描述
	定义一个创建对象的工厂接口，让子类决定实例化哪一个类，将实际创建工作推迟到子类当中。
	
	2.2 使用场景 
	1、日志记录器：记录可能记录到本地硬盘、系统事件、远程服务器等，用户可以选择记录日志到什么地方。 
	2、数据库访问，当用户不知道最后系统采用哪一类数据库，以及数据库可能有变化时。 
	3、设计一个连接服务器的框架，需要三个协议，"POP3"、"IMAP"、"HTTP"，可以把这三个作为产品类，共同实现一个接口。
	
	2.3 优点
	创建对象的接口，让子类决定具体实例化的对象，把简单的内部逻辑判断移到了客户端。工厂方法模式克服了简单工厂所违背的开闭原则的缺点，又保持了封装对象创建过程的优点。扩展性高，想要增加一个产品，只要扩展一个工厂类就可以。

 *
 */
public class Client {
    public static void main(String[] args) {
        Phone phone1=new HuaWeiFactory().createPhone();
        Phone phone2=new XiaoMiFactory().createPhone();
        Phone phone3=new IphoneFactory().createPhone();
        phone1.function();
        phone2.function();
        phone3.function();
    }
}
