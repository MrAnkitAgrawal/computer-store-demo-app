package com.tcs.salesstore.service;

import java.util.ArrayList;
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
				itemDetails.setProductProperties(productProperty);
				
				itemDetailsList.add(itemDetails);
			} else {
				itemDetails.setAmount(itemDetails.getAmount() + amount);
			}	
			
			productItemRepository.save(productItemDetails);
		}
	}
	
	@Transactional
	public String getItemDetails() {
		final String lineSeperator = System.lineSeparator();
		
		StringBuilder treeViewSB = new StringBuilder();
		
		for (ProductType item : productItemRepository.findAll()) {
			treeViewSB.append(item.getProductType() + lineSeperator);
			
			for (ProductProperties properties : item.getProductProperties()) {
				treeViewSB.append("\t" + properties.getPropertyValue() + lineSeperator);
				
				for (ManufactureDetails manufacturer : properties.getManufacturedBy()) {
					treeViewSB.append("\t\t" + manufacturer.getManufacturerName() + lineSeperator);

					for (ItemDetails itemDetails : manufacturer.getItemDetails()) {
						if (itemDetails.getProductProperties().equals(properties)) {
							treeViewSB.append("\t\t\t[ " + itemDetails.getSeriesNumber() + " | " + itemDetails.getPrice() + " | "
									+ itemDetails.getAmount() + " ]" + lineSeperator);
						}
					}
				}
			}
		}
		return treeViewSB.toString();
	}
	
	@Transactional
	public void getItemsTreeFormat() {
		TreeNode root = new TreeNode("Computer Store");
		
		for (ProductType item : productItemRepository.findAll()) {
			TreeNode productTypeNode = new TreeNode(item.getProductType());
			root.addChildNode(productTypeNode);

			for (ProductProperties properties : item.getProductProperties()) {
				TreeNode productPopertiesNode = new TreeNode(properties.getPropertyValue());
				productTypeNode.addChildNode(productPopertiesNode);
				
				for (ManufactureDetails manufacturer : properties.getManufacturedBy()) {
					TreeNode manufacturerNode = new TreeNode(manufacturer.getManufacturerName());
					productPopertiesNode.addChildNode(manufacturerNode);
					
					for (ItemDetails itemDetails : manufacturer.getItemDetails()) {
						if (itemDetails.getProductProperties().equals(properties)) {
							String data = "[ " + itemDetails.getSeriesNumber() + " | " + itemDetails.getPrice() + " | "
									+ itemDetails.getAmount() + " ]";
							
							TreeNode itemNode = new TreeNode(data);
							manufacturerNode.addChildNode(itemNode);
						}
					}
				}

			}
		}
		
		print(root," ");
	}
	
	private void print(TreeNode root, String aling) {
		System.out.println(aling + root.getData());
		root.getChildNodes().forEach(each ->  print(each, aling + aling));
		
	}

	class TreeNode {
		private String data;
		private List<TreeNode> childNodes;
		
		public TreeNode(String data) {
			this.data = data;
			childNodes = new ArrayList<TreeNode>();
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public List<TreeNode> getChildNodes() {
			return childNodes;
		}

		public void setChildNodes(List<TreeNode> childNodes) {
			this.childNodes = childNodes;
		}
		
		public void addChildNode(TreeNode childNode) {
			this.childNodes.add(childNode);
		}

		@Override
		public String toString() {
			return "[data=" + data + "]";
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
