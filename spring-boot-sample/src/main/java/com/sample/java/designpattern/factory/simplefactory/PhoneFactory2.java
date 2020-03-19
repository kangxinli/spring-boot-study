package com.sample.java.designpattern.factory.simplefactory;

public class PhoneFactory2 {
    public static Phone createIpone(){
        return  new Iphone();
    }
    public  static Phone createXiaoMi(){
        return new XiaoMi();
    }
    public static HuanWei createHuaWei(){
        return new HuanWei();
    }
}
