package com.martin.indexy.logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.martin.indexy.helpers.Dater;

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

				System.out.println(Dater.getDateAndTimeString() + "User connected");

				ClientManager clM = new ClientManager(clSocket);

				new Thread(clM).start();
			}
		} catch (Exception e) {
			System.out
					.println("This horrible exception has happended and made the server inoperational:\n"
							+ e.getMessage() + "Press any key to exit");
			try {
				System.in.read();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
