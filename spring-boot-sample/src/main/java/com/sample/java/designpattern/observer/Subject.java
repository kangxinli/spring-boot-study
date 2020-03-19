package com.sample.java.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private List<Observer> list=new ArrayList<Observer>();
    public void register(Observer observer){
        list.add(observer);
    }
    public void removeObserver(Observer observer){
        list.remove(observer);
    }
    public void notifyAlls(){
        for (Observer observer:list){
            observer.update(this);
        }

    }
}
