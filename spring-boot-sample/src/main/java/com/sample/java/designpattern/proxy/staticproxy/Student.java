package com.sample.java.designpattern.proxy.staticproxy;

public class Student implements Person {
	
	private String name;
	
	Student(String name) {
		this.name = name;
	}

	@Override
	public void giveMoney() {
		System.out.println(name + "上交班费50元");
	}

}
