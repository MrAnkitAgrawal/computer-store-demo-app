package com.tcs.salesstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.tcs.salesstore.cli.CommandLauncher;

@SpringBootApplication
public class SalesStoreApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SalesStoreApplication.class, args);

		CommandLauncher commandLauncher = context.getBean(CommandLauncher.class);
		commandLauncher.launchCommandPrompt();
	}
}
