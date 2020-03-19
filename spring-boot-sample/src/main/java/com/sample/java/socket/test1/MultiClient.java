package com.sample.java.socket.test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiClient extends Thread {
	private Socket client;
	
	public MultiClient(Socket c) {
		this.client  = c;
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(  
			        client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream());
			while (true) {  
                String str = in.readLine();  
                System.out.println(str);  
                out.println("has receive....");  
                out.flush();  
                if (str.equals("end"))  
                    break;  
            }  
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(5678);  
        while (true) {  
            // transfer location change Single User or Multi User  
  
            MultiClient mc = new MultiClient(server.accept());  
            mc.start();  
        }  
	}

}
