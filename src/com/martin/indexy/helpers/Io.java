package com.martin.indexy.helpers;

import java.util.Scanner;

public class Io {
	private static Scanner sc = new Scanner(System.in);
	
	public static void out(String out) {
		System.out.println(out);
	}
	
	public static String in() {
		return sc.nextLine();
	}
	
	public static void closeSc() {
		sc.close();
	}

}
