package com.sample.java.socket.test2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 1. 同样定义了通讯端口号，这里给出的端口号必须要和服务器端的一致。
 * 
 * 2. 在main函数里，根据地址信息“localhost”，创建一个InetAddress类型的对象addr。这里，
 * 	      因为我们把客户端和服务器端的代码都放在本机运行，所以同样可以用“127.0.0.1”字符串，来创建InetAddress对象。
 * 
 * 3. 根据addr和端口号信息，创建一个Socket类型对象，该对象用来同服务器端的ServerSocket类型对象交互，共同完成C/S通讯流程。
 * 
 * 4. 同样地创建in和out两类IO句柄，用来向服务器端发送和接收数据流。
 * 
 * 5. 通过out对象，向服务器端发送"Hello Server,I am …"的字符串。发送后，同样可以用in句柄，接收从服务器端的消息。
 * 
 * 6. 利用out对象，发送”byebye”字符串，用以告之服务器端，本次通讯结束。
 * 
 * 7. 在finally从句里，关闭Socket对象，断开同服务器端的连接。
 */

public class ClientCode {

	static String clientName = "Mike";

	// 端口号
	public static int portNo = 3333;

	public static void main(String[] args) throws IOException {

		// 设置连接地址类,连接本地
		InetAddress addr = InetAddress.getByName("localhost");

		// 要对应服务器端的3333端口号
		Socket socket = new Socket(addr, portNo);

		try {
			System.out.println("socket = " + socket);

			// 设置IO句柄
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			out.println("Hello Server,I am " + clientName);

			String str = in.readLine();

			System.out.println(str);

			out.println("byebye");

		} finally {
			System.out.println("close the Client socket and the io.");

			socket.close();
		}
	}
}
