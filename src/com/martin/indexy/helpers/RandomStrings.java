package com.martin.indexy.helpers;

import java.util.Random;

public class RandomStrings {
	private Random random;
	private char[] chars;
	private StringBuilder sb;

	public RandomStrings() {
		super();
		random = new Random();
		chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	}

	public String getRandomString() {
		sb = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

}
