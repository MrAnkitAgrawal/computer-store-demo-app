package com.tcs.salesstore.cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.tcs.salesstore.domain.model.Product;

@Component
public class CSVParser {
	Log log = LogFactory.getLog(CSVParser.class);
	
	public List<CSVItem> parseCSV(Path csvFilePath) throws IOException {
		Function<String, CSVItem> parsingLogic = getLogic();
		List<CSVItem> itemList = Files.readAllLines(csvFilePath).stream().map(parsingLogic).collect(Collectors.toList());
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
		if(tokenList.size() != 6) {
			throw new RuntimeException("Invalid CSV File: Total Value Count Should Be 6!");
		}
		
		CSVItem item = new CSVItem();
		
		if(!Product.getValidProducts().contains(tokenList.get(0).toUpperCase())) {
			throw new RuntimeException("Invalid CSV File: Incorrect ProductType: " + tokenList.get(0));
		}
		final String productType = tokenList.get(0);
		
		if(!tokenList.get(1).chars().allMatch(Character::isDigit)) {
			throw new RuntimeException("Invalid CSV File: Incorrect Amount: " + tokenList.get(1));
		}
		final int amount = Integer.valueOf(tokenList.get(1));

		Map<String, String> itemProperties = tokenList.stream().filter(each -> each.contains(":"))
				.collect(Collectors.toMap(each -> each.split(":")[0], each -> each.split(":")[1]));
		
		if(!isKeyValuePropertiesValid(productType, itemProperties)) {
			throw new RuntimeException("Invalid CSV File: Incorrect Product Properties");
		}
		
		item.setProductType(productType);
		item.setAmount(amount);
		item.setItemProperties(itemProperties);
		
		return item;
	}

	/**
	 * Assuming CSV is containing correct properties.
	 * <<Validation to be implemented>>
	 * 
	 * @param productType
	 * @param itemProperties
	 * @return
	 */
	private boolean isKeyValuePropertiesValid(String productType, Map<String, String> itemProperties) {
		return true;
	}

	public static void main(String args[]) {
		CSVParser parser = new CSVParser();
		try {
			parser.parseCSV(Paths.get("E:\\test.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
