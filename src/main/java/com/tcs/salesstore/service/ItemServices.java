package com.tcs.salesstore.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.salesstore.domain.model.CSVItem;
import com.tcs.salesstore.domain.model.ItemDetails;
import com.tcs.salesstore.domain.model.ManufactureDetails;
import com.tcs.salesstore.domain.model.ProductProperties;
import com.tcs.salesstore.domain.model.ProductType;
import com.tcs.salesstore.domain.repository.ManufacturerRepository;
import com.tcs.salesstore.domain.repository.StoreRepository;

@Service
public class ItemServices {
	@Autowired
	StoreRepository productItemRepository;
	
	@Autowired
	ManufacturerRepository manufacturerRepository;

	@Transactional
	public void saveOrUpdateCSVItemList(List<CSVItem> csvItemList) {
		for (CSVItem csvItem : csvItemList) {
			final String productType = csvItem.getProductType();
			final int amount = csvItem.getAmount();

			Map<String, String> itemProperties = csvItem.getItemProperties();
			final String seriesNumber = itemProperties.get("series_number");
			final String manufacturer = itemProperties.get("manufacturer");
			final double price = Double.valueOf(itemProperties.get("price"));
			final String productPropertyName = getProductProperty(productType);
			final String productPropertyValue = itemProperties.get(productPropertyName);

			ProductType productItemDetails = productItemRepository.findByProductType(productType);
			if (productItemDetails == null) {
				productItemDetails = new ProductType();
				productItemDetails.setProductType(productType);
			}

			List<ProductProperties> productPropertiesList = productItemDetails.getProductProperties();
			ProductProperties productProperty = productPropertiesList.stream()
					.filter(each -> each.getPropertyValue().equals(productPropertyValue)).findFirst().orElse(null);
			if(productProperty == null) {
				productProperty = new ProductProperties();
				productProperty.setPropertyName(productPropertyName);
				productProperty.setPropertyValue(productPropertyValue);
				
				productPropertiesList.add(productProperty);
			}
			

			List<ManufactureDetails> manufacturerList = productProperty.getManufacturedBy();
			ManufactureDetails manufacturerDetails = manufacturerList.stream()
					.filter(each -> each.getManufacturerName().equals(manufacturer)).findFirst().orElse(null);
			if(manufacturerDetails == null) {
				manufacturerDetails = manufacturerRepository.findByManufacturerName(manufacturer);
				if (manufacturerDetails == null) {
					manufacturerDetails = new ManufactureDetails();
					manufacturerDetails.setManufacturerName(manufacturer);
				}
				manufacturerList.add(manufacturerDetails);
			}

			List<ItemDetails> itemDetailsList = manufacturerDetails.getItemDetails();
			ItemDetails itemDetails = itemDetailsList.stream()
					.filter(each -> each.getSeriesNumber().equals(seriesNumber)).findFirst().orElse(null);
			if (itemDetails == null) {
				itemDetails = new ItemDetails();
				itemDetails.setSeriesNumber(seriesNumber);
				itemDetails.setPrice(price);
				itemDetails.setAmount(amount);
				
				itemDetailsList.add(itemDetails);
			} else {
				itemDetails.setAmount(itemDetails.getAmount() + amount);
			}	
			
			productItemRepository.save(productItemDetails);
		}
	}

	private String getProductProperty(String productType) {
		String key = null;
		if (productType.equalsIgnoreCase("Computer")) {
			key = "form-factor";
		} else if (productType.equalsIgnoreCase("laptop")) {
			key = "size";
		} else if (productType.equalsIgnoreCase("monitor")) {
			key = "resolution";
		} else if (productType.equalsIgnoreCase("hdd")) {
			key = "capacity";
		}
		return key;
	}
}
