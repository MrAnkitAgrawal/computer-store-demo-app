package com.tcs.salesstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.tcs.salesstore.config.CommandLauncher;

@SpringBootApplication
public class SalesStoreApplication implements CommandLineRunner {
	public static void main(String[] args) {
		ApplicationContext context =  SpringApplication.run(SalesStoreApplication.class, args);
		
		CommandLauncher cmdLauncher = context.getBean(CommandLauncher.class);
		cmdLauncher.launchCommandPrompt();
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
