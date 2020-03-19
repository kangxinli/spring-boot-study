package com.sample.java.socket.test2;

import java.net.InetAddress;

public class ThreadClient {

	public static void main(String[] args) throws Exception {
		int threadNo = 0;

		InetAddress addr = InetAddress.getByName("localhost");

		for (threadNo = 0; threadNo < 3; threadNo++) {
			new ClientThreadCode(addr);
		}
	}

}
