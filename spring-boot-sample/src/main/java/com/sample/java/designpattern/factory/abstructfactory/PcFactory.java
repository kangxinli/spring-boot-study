package com.sample.java.designpattern.factory.abstructfactory;

public class PcFactory implements ElectronicFactory {

    @Override
    public CPU createCpu() {
        return new Pcpu();
    }

    @Override
    public GraphicsProcessor createG() {
        return new PcG();
    }

    @Override
    public Screen createScreen() {
        return new PcScreen();
    }
}
