package com.sample.java.designpattern.factory.factorymethod;


public class IphoneFactory implements PhoneFactory {

    @Override
    public Phone createPhone() {
        return new Iphone();
    }
}
