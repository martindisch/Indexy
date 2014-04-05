package com.martin.indexy.helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Io {

	private BufferedWriter WrOut;
	private BufferedReader ReIn;

	public Io(BufferedWriter out, BufferedReader in) {
		super();
		this.WrOut = out;
		this.ReIn = in;
	}

	public void out(String out) {
		try {
			WrOut.write(out);
			WrOut.newLine();
			WrOut.flush();
			System.out.println(Dater.dateString() + out);
		} catch (IOException e) {
			System.out.println(Dater.dateString() + e.getMessage());
		}
	}

	public void outSingle(String out) {
		try {
			WrOut.write(out);
			WrOut.flush();
			System.out.print(Dater.dateString() + out);
		} catch (IOException e) {
			System.out.println(Dater.dateString() + e.getMessage());
		}
	}

	public String in() {
		String rep = null;
		try {
			rep = ReIn.readLine();
		} catch (IOException e) {
			System.out.println(Dater.dateString() + e.getMessage());
		}
		return rep;
	}

	public void close() {
		try {
			ReIn.close();
			WrOut.close();
		} catch (IOException e) {
			System.out.println(Dater.dateString() + e.getMessage());
		}
	}

}
