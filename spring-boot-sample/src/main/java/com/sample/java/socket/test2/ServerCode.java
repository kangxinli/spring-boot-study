package com.sample.java.socket.test2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 1. 在上述代码里的main函数前，我们设置了通讯所用到的端口号，为3333。
 * 
 * 2. 在main函数里，根据给定3333端口号，初始化一个ServerSocket对象s，该对象用来承担服务器端监听连接和提供通讯服务的功能。
 * 
 * 3. 调用ServerSocket对象的accept方法，监听从客户端的连接请求。当完成调用accept方法后，整段服务器端代码将回阻塞在这里，直到客户端发来connect请求。
 * 
 * 4. 当客户端发来connect请求，或是通过构造函数直接把客户端的Socket对象连接到服务器端后，阻塞于此的代码将会继续运行。
 *    此时服务器端将会根据accept方法的执行结果，用一个Socket对象来描述客户端的连接句柄。
 * 
 * 5. 创建两个名为in和out的对象，用来传输和接收通讯时的数据流。
 * 
 * 6. 创建一个while(true)的死循环，在这个循环里，通过in.readLine()方法，读取从客户端发送来的IO流（字符串），并打印出来。
 * 	      如果读到的字符串是“byebye”，那么退出while循环。
 * 
 * 7. 在try…catch…finally语句段里，不论在try语句段里是否发生异常，并且不论这些异常的种类，finally从句都将会被执行到。
 *    在finally从句里，将关闭描述客户端的连接句柄socket对象和ServerSocket类型的s对象。
 * 
 */

public class ServerCode {

	public static int port = 3333;

	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(port);

		System.out.println("The Server is start: " + server);

		// 阻塞,直到有客户端连接
		Socket socket = server.accept();

		try {
			System.out.println("Accept the Client: " + socket);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			while (true) {
				String str = in.readLine();
				if (str.equals("byebye")) {
					break;
				}
				System.out.println("In Server reveived the info: " + str);

				out.println(str);
			}
		} finally {
			System.out.println("close the Server socket and the io.");
			socket.close();
			server.close();
		}

	}

}
