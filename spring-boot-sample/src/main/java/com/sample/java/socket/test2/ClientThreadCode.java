package com.sample.java.socket.test2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 1. 在构造函数里， 通过参数类型为InetAddress类型参数和3333，初始化了本类里的Socket对象，随后实例化了两类IO对象，并通过start方法
 * ，启动定义在run方法内的本线程的业务逻辑。
 * 
 * 2. 在定义线程主体动作的run方法里，通过IO句柄，向Socket信道上传输本客户端的ID号，发送完毕后，传输”byebye”字符串，
 *    向服务器端表示本线程的通讯结束。
 * 
 * 3. 同样地，catch从句将处理在try语句里遇到的IO错误等异常，而在finally从句里，将在通讯结束后关闭客户端的Socket句柄。
 * 
 */

public class ClientThreadCode extends Thread {

	// 客户端的socket
	private Socket socket;

	// 线程统计数，用来给线程编号
	private static int cnt = 0;

	private int clientId = cnt++;

	private BufferedReader in;

	private PrintWriter out;

	// 构造函数
	public ClientThreadCode(InetAddress addr) {
		try {
			socket = new Socket(addr, 3333);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 实例化IO对象
		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())), true);

			// 开启线程
			start();
		} catch (IOException e) {
			// 出现异常，关闭socket
			try {
				socket.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	// 线程主体方法
	public void run() {
		try {
			out.println("Hello Server,My id is " + clientId);

			String str = in.readLine();
			System.out.println(str);

			out.println("byebye");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
