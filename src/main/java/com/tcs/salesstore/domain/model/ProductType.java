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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product_type", catalog = "computer_store")
public class ProductType implements Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	@Column(name = "product_type")
	String productType;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "pid")
	List<ProductProperties> productProperties = new ArrayList<>();

	public ProductType() {
	}

	public ProductType(String productType, List<ProductProperties> productProperties) {
		this.productType = productType;
		this.productProperties = productProperties;
	}

	public int getId() {
		return id;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public List<ProductProperties> getProductProperties() {
		return productProperties;
	}

	public void setProductProperties(List<ProductProperties> productProperties) {
		this.productProperties = productProperties;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, productProperties, productType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductType other = (ProductType) obj;
		return id == other.id && Objects.equals(productProperties, other.productProperties)
				&& Objects.equals(productType, other.productType);
	}

	@Override
	public String toString() {
		return "ProductType [id=" + id + ", productType=" + productType + ", productProperties=" + productProperties
				+ "]";
	}
}
