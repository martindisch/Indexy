package com.martin.indexy.helpers;

import com.martin.indexy.types.Action;
import com.martin.indexy.types.Commands;

public class Parser {

	public static Action getAction(String input) {
		String[] parts = input.split(" ");
		String sCommand = parts[0];
		String data = "";

		for (int i = 1; i < parts.length; i++) {
			data += parts[i] + " ";
		}

		data = data.trim();

		Commands cCommand = null;
		for (Commands act : Commands.values()) {
			if (sCommand.toLowerCase().contentEquals(
					((Commands) act).name().toLowerCase())) {
				cCommand = act;
			}
		}

		if (cCommand != null) {
			return new Action(cCommand, data);
		}
		return new Action(Commands.UNKNOWN, data);
	}

}
