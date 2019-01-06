package com.tcs.salesstore.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="manufacturer_details", catalog="computer_store")
public class ManufactureDetails implements Serializable {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="manufacturer")
	private String manufacturerName;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="mid")
	private List<ItemDetails> itemDetails = new ArrayList<>();
	
	@ManyToMany(mappedBy="manufacturedBy")
	private List<ProductProperties> productProperties = new ArrayList<>();
	
	public ManufactureDetails() {
	}

	public ManufactureDetails(String manufacturerName, List<ItemDetails> itemDetails) {
		this.manufacturerName = manufacturerName;
		this.itemDetails = itemDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public List<ItemDetails> getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(List<ItemDetails> itemDetails) {
		this.itemDetails = itemDetails;
	}
	
	public List<ProductProperties> getProductProperties() {
		return productProperties;
	}

	public void setProductProperties(List<ProductProperties> productProperties) {
		this.productProperties = productProperties;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, itemDetails, manufacturerName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManufactureDetails other = (ManufactureDetails) obj;
		return id == other.id && Objects.equals(itemDetails, other.itemDetails)
				&& Objects.equals(manufacturerName, other.manufacturerName);
	}

	@Override
	public String toString() {
		return "ManufactureDetails [id=" + id + ", manufacturerName=" + manufacturerName + ", itemDetails="
				+ itemDetails + "]";
	}
}
