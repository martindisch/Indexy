package com.martin.indexy.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.martin.indexy.helpers.Dater;
import com.martin.indexy.helpers.IndexHolder;
import com.martin.indexy.types.Entry;

public class Server {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		final int backlog = 10;
		final int port = 23;

		try {
			@SuppressWarnings("resource")
			ServerSocket seSocket = new ServerSocket(port, backlog);
			System.out.println("Server up and running...");
			
			try {
				File f = new File("db");
				if (f.exists()) {
					FileInputStream fis = new FileInputStream(f);
					ObjectInputStream ois = new ObjectInputStream(fis);

					long millis = System.currentTimeMillis();
					System.out.println("Reading from disk...");
					IndexHolder.entries = (ArrayList<Entry>) ois.readObject();
					ois.close();
					System.out.println("Data has been read successfully in "
							+ (System.currentTimeMillis() - millis) + "ms");
				}
				
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
			} catch (IOException e) {
				System.out.println("Some IOException just happened");
			} catch (ClassNotFoundException e) {
				System.out.println("The database is not compatible with this version");
			}
			
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
