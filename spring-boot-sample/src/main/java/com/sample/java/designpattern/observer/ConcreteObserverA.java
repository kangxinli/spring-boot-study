package com.sample.java.designpattern.observer;

public class ConcreteObserverA implements Observer {
    private int state;
    @Override
    public void update(Subject subject) {
        state=((ConcreteSubject)subject).getState();

    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
