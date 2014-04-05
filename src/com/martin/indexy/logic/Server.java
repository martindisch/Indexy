package com.martin.indexy.logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		final int backlog = 10;
		final int port = 1234;
		
		try {
			@SuppressWarnings("resource")
			ServerSocket seSocket = new ServerSocket(port, backlog);
			System.out.println("Server up and running...");
			for (;;) {
				Socket clSocket = seSocket.accept();
				
				ClientManager clM = new ClientManager(clSocket);

				new Thread(clM).start();
			}
		}
		catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

}
