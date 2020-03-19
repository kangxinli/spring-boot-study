package com.sample.java8.test;

import java.util.Optional;

public class Java8OptionalTester {

	public static void main(String[] args) {
		Java8OptionalTester tester = new Java8OptionalTester();
		
		Integer value1 = null;
		Integer value2 = new Integer(10);
		
		Optional<Integer> a = Optional.ofNullable(value1);
		Optional<Integer> b = Optional.of(value2);
		
		System.out.println(tester.sum(a, b));

	}
	
	public Integer sum(Optional<Integer> a, Optional<Integer> b) {
		System.out.println("First parameter is present: " + a.isPresent());
		System.out.println("First parameter is present: " + b.isPresent());
		
		Integer value1 = a.orElse(new Integer(0));
		
		Integer value2 = b.get();
		
		return value1 + value2;
		
	}

}
