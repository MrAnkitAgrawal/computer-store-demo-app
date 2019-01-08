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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product_properties", catalog = "computer_store")
public class ProductProperties implements Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "property_name")
	private String propertyName;

	@Column(name = "property_value")
	private String propertyValue;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Product_Manufacturer", joinColumns = @JoinColumn(name = "PID"), inverseJoinColumns = @JoinColumn(name = "MID"))
	List<ManufactureDetails> manufacturedBy = new ArrayList<>();

	public ProductProperties() {
	}

	public ProductProperties(String propertyName, String propertyValue, List<ManufactureDetails> manufacturedBy) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.manufacturedBy = manufacturedBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public List<ManufactureDetails> getManufacturedBy() {
		return manufacturedBy;
	}

	public void setManufacturedBy(List<ManufactureDetails> manufacturedBy) {
		this.manufacturedBy = manufacturedBy;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, manufacturedBy, propertyName, propertyValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductProperties other = (ProductProperties) obj;
		return id == other.id && Objects.equals(manufacturedBy, other.manufacturedBy)
				&& Objects.equals(propertyName, other.propertyName)
				&& Objects.equals(propertyValue, other.propertyValue);
	}

	@Override
	public String toString() {
		return "ProductProperties [id=" + id + ", propertyName=" + propertyName + ", propertyValue=" + propertyValue
				+ ", manufacturedBy=" + manufacturedBy + "]";
	}

}
