package com.tcs.salesstore.cli;

import java.io.Console;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLauncher {
	Log log = LogFactory.getLog(CommandLauncher.class);

	@Autowired
	CommandExecutor executor;

	public void launchCommandPrompt() {
		Console console = System.console();
		console.printf("%s\n", welcomeMsg);
		console.printf("%s\n", helpMsg);

		String cmdStr = null;

		while (true) {
			try {
				cmdStr = console.readLine(cmdPrompt);
				String[] commandTokens = cmdStr.split("\\s+");
				log.debug("cmdStr: " + "\ncommandTokens: " + commandTokens);

				boolean isCommandValid = validateCommand(commandTokens);
				if (!isCommandValid) {
					console.printf("%s\n%s\n", invalidCmdMsg, cmdStr);
					console.printf("%s\n", helpMsg);
				} else {
					if (cmdStr.equalsIgnoreCase(Command.EXIT.name())) {
						return;
					}
					executeCommand(commandTokens);
				}
			} catch (Exception e) {
				console.printf("%s\n%s\n", invalidCmdMsg, cmdStr);
				console.printf("%s\n", helpMsg);
				log.error("Error during command parsing", e);
			}
		}

	}

	private boolean validateCommand(String[] commandTokens) {
		boolean isCommandValid = false;

		if (commandTokens.length > 2) {
			return false;
		}

		List<String> validCommands = Command.getValidCommands();
		if (!validCommands.contains(commandTokens[0].toUpperCase())) {
			return false;
		}

		Command command = Command.valueOf(commandTokens[0].toUpperCase());
		switch (command) {
		case IMPORT:
		case EXPORT:
		case EXIT:
			isCommandValid = true;
			break;
		default:
			isCommandValid = false;
		}
		return isCommandValid;
	}

	private void executeCommand(String[] commandTokens) {
		Command command = Command.valueOf(commandTokens[0].toUpperCase());
		switch (command) {
		case IMPORT:
			executor.importItems(commandTokens[1]);
			break;
		case EXPORT:
			executor.exportItems(commandTokens[1]);
			break;
		default:
			return;
		}
	}

	final String helpMsg = "\nValid Commands:\n" + "  import <csv-file>\n" + "  export <file>\n" + "  exit\n";
	final String invalidCmdMsg = "Invalid Command!";
	final String cmdPrompt = "Command>";
	final String welcomeMsg = "Welcome to Computer Store Application";
}
