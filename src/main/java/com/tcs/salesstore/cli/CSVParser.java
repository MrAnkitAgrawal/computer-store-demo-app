package com.tcs.salesstore.cli;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import com.tcs.salesstore.domain.model.CSVItem;

@Component
public class CSVParser {
	Log log = LogFactory.getLog(CSVParser.class);
	
	private String csvFilePath;
	
	public CSVParser() {
	}

	public CSVParser(String csvFilePath) {
		this.csvFilePath = csvFilePath;
	}
	
	public String getCsvFilePath() {
		return csvFilePath;
	}

	public void setCsvFilePath(String csvFilePath) {
		this.csvFilePath = csvFilePath;
	}

	public List<CSVItem> parseCSV() throws FileNotFoundException {
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
		Function<String, CSVItem> parsingLogic = getLogic();
		List<CSVItem> itemList = br.lines().map(parsingLogic).collect(Collectors.toList());
		log.debug(itemList);
		return itemList;
	}

	private Function<String, CSVItem> getLogic() {
		Function<String, CSVItem> function = eachItemLine -> {
			log.debug(eachItemLine);
			StringTokenizer st = new StringTokenizer(eachItemLine, ";");
			List<String> tokenList = new ArrayList<>();
			while (st.hasMoreTokens()) {
				tokenList.add(st.nextToken());
			}
			CSVItem item = mapToItemObj(tokenList);
			log.debug(item);
			return item;
		};
		return function;
	}

	private CSVItem mapToItemObj(List<String> tokenList) {
		CSVItem item = new CSVItem();
		
		item.setProductType(tokenList.get(0));
		item.setAmount(Integer.valueOf(tokenList.get(1)));

		Map<String, String> itemProperties = tokenList.stream().filter(each -> each.contains(":"))
				.collect(Collectors.toMap(each -> each.split(":")[0], each -> each.split(":")[1]));
		item.setItemProperties(itemProperties);
		
		boolean isItemValid = validateItem(item);
		if(!isItemValid) {
			throw new RuntimeException("Incorrect CSV");
		}
		return item;
	}

	private boolean validateItem(CSVItem item) {
		return true;
	}

	public static void main(String args[]) {
		CSVParser parser = new CSVParser();
		parser.setCsvFilePath("E:\\test.csv");
		try {
			parser.parseCSV();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
