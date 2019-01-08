package com.tcs.salesstore.domain.model;

import java.util.ArrayList;
import java.util.List;

public enum Product {
	COMPUTER, LAPTOP, MONITOR, HDD;
	
	public static List<String> getValidProducts() {
		List<String> productList = new ArrayList<>();
		for(Product product : Product.values()) {
			productList.add(product.name());
		}
		return productList;
	}
}
