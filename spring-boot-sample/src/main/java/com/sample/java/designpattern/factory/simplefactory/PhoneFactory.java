package com.sample.java.designpattern.factory.simplefactory;

public class PhoneFactory {
    public static Phone createPhone(String type){
        if ("小米".equals(type)){
            return new XiaoMi();
        }
        else if ("华为".equals(type)){
            return new HuanWei();
        }
        else if ("Iphone".equals(type)){
            return new Iphone();

        }
        else return  null;

    }
}
