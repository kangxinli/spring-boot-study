package com.sample.java8.test;

public class Java8Tester2 {
	private final static String salutation = "Hello ";

	public static void main(String[] args) {
		GreetingService greetingService = msg -> System.out.println(salutation + msg);
		
		greetingService.sayMessage("Mahesh");

	}
	
	interface GreetingService {
		void sayMessage(String message);
	}

}
