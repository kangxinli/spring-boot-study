package com.sample.java.designpattern.factory.factorymethod;

public class XiaoMiFactory implements PhoneFactory {

    @Override
    public Phone createPhone() {
        return new XiaoMi();
    }
}
