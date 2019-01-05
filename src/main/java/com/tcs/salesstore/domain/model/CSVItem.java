package com.tcs.salesstore.domain.model;

import java.util.HashMap;
import java.util.Map;

public class CSVItem {
	String productType;
	Integer amount;
	Map<String, String> itemProperties = new HashMap<>();

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Map<String, String> getItemProperties() {
		return itemProperties;
	}

	public void setItemProperties(Map<String, String> itemProperties) {
		this.itemProperties = itemProperties;
	}

	@Override
	public String toString() {
		return "CSVItem [productType=" + productType + ", amount=" + amount + ", itemProperties=" + itemProperties
				+ "]";
	}
}
