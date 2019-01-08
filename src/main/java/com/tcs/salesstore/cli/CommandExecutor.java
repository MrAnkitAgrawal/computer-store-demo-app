package com.tcs.salesstore.cli;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tcs.salesstore.domain.model.CSVItem;
import com.tcs.salesstore.service.ItemServices;

@Component
public class CommandExecutor {
	Log log = LogFactory.getLog(CommandExecutor.class);
	
	@Autowired
	CSVParser parser;
	
	@Autowired
	ItemServices itemService;
	
	public void importItems(final String fileStr) {
		log.debug("fileStr: " + fileStr);
		
		if (fileStr == null) {
			throw new RuntimeException("File is null");
		}
		
		try {
			parser.setCsvFilePath(fileStr);
			List<CSVItem> csvItemList = parser.parseCSV();
			log.debug(csvItemList);
			itemService.saveOrUpdateCSVItemList(csvItemList);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException("File not found!");
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			throw new RuntimeException(re.getMessage());
		}
	}
	
	public void exportItems(final String fileStr) {
		log.debug("fileStr: " + fileStr);
		
		if (fileStr == null) {
			throw new RuntimeException("File is null");
		}
		
		itemService.getItemDetails();
		
	}

}
