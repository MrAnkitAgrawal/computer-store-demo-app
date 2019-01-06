package com.tcs.salesstore;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tcs.salesstore.config.CSVParser;
import com.tcs.salesstore.domain.model.CSVItem;
import com.tcs.salesstore.service.ItemServices;

@SpringBootApplication
public class SalesStoreApplication implements CommandLineRunner {
	Log log = LogFactory.getLog(SalesStoreApplication.class);
	
	@Autowired
	ItemServices itemServices;
	
	@Autowired
	CSVParser csvParser;
	
	public static void main(String[] args) {
		SpringApplication.run(SalesStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		csvParser.setCsvFilePath("E:\\test1.csv");
		try {	
			List<CSVItem> csvItemList = csvParser.parseCSV();
			log.debug(csvItemList);
			itemServices.saveOrUpdateCSVItemList(csvItemList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
}
