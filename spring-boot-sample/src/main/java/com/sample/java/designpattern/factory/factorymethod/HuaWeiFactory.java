package com.sample.java.designpattern.factory.factorymethod;

public class HuaWeiFactory implements PhoneFactory{

    @Override
    public Phone createPhone() {
        return new HuanWei();
    }
}
