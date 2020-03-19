package com.sample.java.socket.test2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 1. 这个类通过继承Thread类来实现线程的功能，也就是说，在其中的run方法里，定义了该线程启动后要执行的业务动作。
 * 
 * 2. 这个类提供了两种类型的重载函数。在参数类型为Socket的构造函数里，
 *    通过参数，初始化了本类里的Socket对象，同时实例化了两类IO对象。在此基础上，通过start方法，启动定义在run方法内的本线程的业务逻辑。
 * 
 * 3. 在定义线程主体动作的run方法里，通过一个for(;;)类型的循环，根据IO句柄，读取从Socket信道上传输过来的客户端发送的通讯信息。
 *    如果得到的信息为“byebye”，则表明本次通讯结束，退出for循环。
 * 
 * 4. catch从句将处理在try语句里遇到的IO错误等异常，而在finally从句里，将在通讯结束后关闭客户端的Socket句柄。
 * 
 */

public class ServerThreadCode extends Thread {

	// 客户端的socket
	private Socket clientSocket;

	// IO句柄
	private BufferedReader sin;
	private PrintWriter sout;

	// 默认的构造函数
	public ServerThreadCode() {
	}

	public ServerThreadCode(Socket s) throws IOException {

		clientSocket = s;
		// 初始化sin和sout的句柄
		sin = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		sout = new PrintWriter(clientSocket.getOutputStream(), true);

		// 开启线程
		start();

	}

	// 线程执行的主体函数
	public void run() {
		try {
			// 用循环来监听通讯内容
			for (;;) {
				String str = sin.readLine();

				// 如果接收到的是byebye，退出本次通讯
				if (str.equals("byebye")) {
					break;
				}
				System.out.println("In Server reveived the info: " + str);

				sout.println(str);

			}
			System.out.println("closing the server socket!");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("close the Server socket and the io.");

			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
