package com.sample.java.thread.synchronizedd;

public class AccountingSync implements Runnable {
	
	static int i = 0;
	
	public synchronized void increase() {
		i++;
	}

	@Override
	public void run() {
		for (int j = 0; j < 100000; j++) {
			increase();
		}
	}
	
	public static void main(String[] args) throws Exception {
		AccountingSync instance = new AccountingSync();
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
	}

}
