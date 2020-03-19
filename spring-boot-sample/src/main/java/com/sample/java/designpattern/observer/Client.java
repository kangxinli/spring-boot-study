package com.sample.java.designpattern.observer;

/**
 * 观察者模式
 * 
 * 观察者模式的定义：

　　在对象之间定义了一对多的依赖，这样一来，当一个对象改变状态，依赖它的对象会收到通知并自动更新。
 */
public class Client {
    public static void main(String[] args) {
        //目标对象
        ConcreteSubject subject=new ConcreteSubject();
        //创建多个观察者
        ConcreteObserverA coa=new ConcreteObserverA();
        ConcreteObserverA coa2=new ConcreteObserverA();
        ConcreteObserverA coa3=new ConcreteObserverA();
        //让这三个观察者添加到subject观察者队伍中
        subject.register(coa);
        subject.register(coa2);
        subject.register(coa3);
        subject.setState(1234);
        System.out.println(coa.getState());
        System.out.println(coa2.getState());
        System.out.println(coa3.getState());
        subject.removeObserver(coa3);
        subject.setState(5678);
        System.out.println(coa.getState());
        System.out.println(coa2.getState());
        System.out.println(coa3.getState());
    }
}
