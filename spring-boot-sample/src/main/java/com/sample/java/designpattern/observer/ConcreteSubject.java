package com.sample.java.designpattern.observer;

public class ConcreteSubject  extends Subject{
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        this.notifyAlls();//更新所有的订阅者

    }
}
