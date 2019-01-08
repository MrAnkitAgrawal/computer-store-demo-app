package com.tcs.salesstore.cli;

import java.util.ArrayList;
import java.util.List;

public enum Command {
	IMPORT, EXPORT, EXIT;

	public static List<String> getValidCommands() {
		List<String> validCommands = new ArrayList<>();
		for (Command validCmd : Command.values()) {
			validCommands.add(validCmd.name());
		}
		return validCommands;
	}
}
