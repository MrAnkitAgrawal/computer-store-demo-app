package com.tcs.salesstore.config;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tcs.salesstore.domain.model.CSVItem;
import com.tcs.salesstore.service.ItemServices;

@Component
public class CommandLauncher {
	Log log = LogFactory.getLog(CommandLauncher.class);
	
	@Autowired
	ItemServices itemServices;
	
	@Autowired
	CSVParser csvParser;
	
	public void launchCommandPrompt() {
		System.out.println(welcomeMsg);
		System.out.println(helpMsg);
		
		//BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		Console console = System.console();
		//console.printf("%s", welcomeMsg);
		//console.printf("%s", helpMsg);
		
		String cmdStr = null;
		try {
			while(true) {
				cmdStr = console.readLine(cmdPrompt);
				
				if(cmdStr.equalsIgnoreCase("exit")) {
					return;
				}
				
				boolean isCommandValid = validateCommand(cmdStr);
				if (!isCommandValid) {
					System.out.println(invalidCmdMsg);
					System.out.println(helpMsg);
				} else {
					executeCommand(cmdStr);
				}
				System.out.println("cmdStr: " + cmdStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void executeCommand(String cmdStr) {	
		csvParser.setCsvFilePath("E:\\test.csv");
		try {	
			List<CSVItem> csvItemList = csvParser.parseCSV();
			log.debug(csvItemList);
			itemServices.saveOrUpdateCSVItemList(csvItemList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}

	private boolean validateCommand(String cmdStr) {
		boolean isCommandValid = true;
		
		return isCommandValid;
	}

	final String helpMsg = "\nValid Commands:\n"
			+ "  import <csv-file>\n"
			+ "  export <file>\n"
			+ "  exit\n";
	final String invalidCmdMsg = "Invalid Command!";
	final String cmdPrompt = "Command>";
	final String welcomeMsg = "Welcome to Computer Store Application";	

}
