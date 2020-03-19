package com.sample.java.designpattern.factory.abstructfactory;

public interface ElectronicFactory {
    CPU createCpu();
    GraphicsProcessor createG();
    Screen createScreen();
}
