package com.martin.indexy.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.martin.indexy.helpers.Dater;
import com.martin.indexy.helpers.IndexManager;
import com.martin.indexy.helpers.Io;
import com.martin.indexy.helpers.Parser;
import com.martin.indexy.types.Action;

public class ClientManager implements Runnable {

	private Socket clSocket;
	private Io io;
	private IndexManager indexManager;

	public ClientManager(Socket clSocket) {
		super();
		this.clSocket = clSocket;
		try {
			io = new Io(new BufferedWriter(new OutputStreamWriter(
					clSocket.getOutputStream(), "UTF-8")), new BufferedReader(
					new InputStreamReader(clSocket.getInputStream(), "UTF-8")));
			indexManager = new IndexManager(io);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {

		String input;
		displayCommands();
		io.outSingle("\n");

		try {
			while (!(input = io.in()).toLowerCase().contentEquals("end")) {
				try {
					Action action = Parser.getAction(input);
					doAction(action);
					io.outSingle("\n");
				} catch (Exception e) {
					io.out("A strange exception has just occurred: "
							+ e.getMessage()
							+ "\nBut don't worry, business as usual\n");
				}
			}
		}
		catch (Exception e) {
			// Yeah, crap... Let's hope it was really a quit
		}
		finally {
			System.out.println(Dater.dateString() + "User has quit");
			indexManager.write("db");
			io.close();
			try {
				clSocket.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void doAction(Action action) {
		switch (action.getCommand()) {
		case GENERATE:
			indexManager.generate(Integer.parseInt(action.getData()));
			break;
		case WRITE:
			indexManager.write(action.getData());
			break;
		case READ:
			indexManager.read(action.getData());
			break;
		case ADD:
			indexManager.add(action.getData());
			break;
		case SEARCH:
			indexManager.search(action.getData());
			break;
		case LIST:
			indexManager.list(action.getData());
			break;
		case DELETE:
			indexManager.delete(action.getData());
			break;
		case DISPLAY:
			indexManager.display(action.getData());
			break;
		case HELP:
			displayCommands();
			break;
		case UNKNOWN:
			io.out("Unknown command");
			break;
		}
	}

	private void displayCommands() {
		io.out("\nAvailable commands:\n");
		io.out(" - End (Exit the application)");
		io.out(" - Generate [amount] (Add a number of random Entries with random titles and tags to the currently loaded collection)");
		io.out(" - Write [name] (Save the currently loaded collection to disk)");
		io.out(" - Read [name] (Load a collection from a file)");
		io.out(" - Add [title] (Add a new entry to the currently loaded collection)");
		io.out(" - Search [part of title or tag] (Search the currently loaded collection)");
		io.out(" - List [start] [end (optional)] (List entries [start] to [end]");
		io.out(" - Delete [number] (Delete this entry)");
		io.out(" - Display [number] (Display this entry)");
		io.out(" - Help (Display this help)");
	}

}
